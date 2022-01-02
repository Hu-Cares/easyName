 
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.ItemaUserVO;
import ltd.newbee.mall.dao.MallShopMapper;
import ltd.newbee.mall.dao.MallUserMapper;
import ltd.newbee.mall.dao.ItemaMallCouponMapper;
import ltd.newbee.mall.dao.ItemaMallUserCouponRecordMapper;
import ltd.newbee.mall.entity.*;
import ltd.newbee.mall.service.ItemaMallUserService;
import ltd.newbee.mall.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;

@Service
public class ItemaMallUserServiceImpl implements ItemaMallUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    @Autowired
    private MallShopMapper mallShopMapper;

    @Autowired
    private ItemaMallCouponMapper itemaMallCouponMapper;

    @Autowired
    private ItemaMallUserCouponRecordMapper itemaMallUserCouponRecordMapper;

    @Override
    public PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        Iterator<MallUser> it = mallUsers.iterator();
        while(it.hasNext()){
            MallUser x = it.next();
            if((x.getIsMerchant())!=1){
                it.remove();
            }
        }
        for(MallUser x:mallUsers){
            x.setNickName((mallShopMapper.selectByLoginName(x.getLoginName())).getShopName());
            x.setCreateTime((mallShopMapper.selectByLoginName(x.getLoginName())).getCreateTime());
        }
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, Constants.UTF_ENCODING);
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) <= 0) {
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        // 添加注册赠卷
        List<ItemaMallCoupon> itemaMallCoupons = itemaMallCouponMapper.selectAvailableGiveCoupon();
        for (ItemaMallCoupon itemaMallCoupon : itemaMallCoupons) {
            ItemaMallUserCouponRecord couponUser = new ItemaMallUserCouponRecord();
            couponUser.setUserId(registerUser.getUserId());
            couponUser.setCouponId(itemaMallCoupon.getCouponId());
            itemaMallUserCouponRecordMapper.insertSelective(couponUser);
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

//商家注册

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String shop_register(String shopName, String idCard,String realName,String loginName) {

        if (mallShopMapper.selectByShopName(shopName) != null) {
            return ServiceResultEnum.SAME_SHOP_NAME_EXIST.getResult();
        }
        Shop registerShop = new Shop();
        registerShop.setShopName(shopName);
        registerShop.setIdCard(idCard);
        registerShop.setRealName(realName);
        registerShop.setLoginName(loginName);
        if (mallShopMapper.insertSelective(registerShop) <= 0) {
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        mallUserMapper.isMerchant(loginName);
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public String login(String loginName, String passwordMD5, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {
            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            if(user.getIsMerchant()==1){
              Shop shop=mallShopMapper.selectByLoginName(loginName);  //从商家中获取店铺ID和店铺名以及姓名
              httpSession.setAttribute(Constants.MALL_SHOP_SESSION_KEY, shop);
            }
            ItemaUserVO itemaUserVO = new ItemaUserVO();
            BeanUtil.copyProperties(user, itemaUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, itemaUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public ItemaUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        ItemaUserVO userTemp = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        MallUser userFromDB = mallUserMapper.selectByPrimaryKey(userTemp.getUserId());
        if (userFromDB != null) {
            if (!StringUtils.isEmpty(mallUser.getNickName())) {
                userFromDB.setNickName(NewBeeMallUtils.cleanString(mallUser.getNickName()));
            }
            if (!StringUtils.isEmpty(mallUser.getAddress())) {
                userFromDB.setAddress(NewBeeMallUtils.cleanString(mallUser.getAddress()));
            }
            if (!StringUtils.isEmpty(mallUser.getIntroduceSign())) {
                userFromDB.setIntroduceSign(NewBeeMallUtils.cleanString(mallUser.getIntroduceSign()));
            }
            if (mallUserMapper.updateByPrimaryKeySelective(userFromDB) > 0) {
                ItemaUserVO itemaUserVO = new ItemaUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, itemaUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, itemaUserVO);
                return itemaUserVO;
            }
        }
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
