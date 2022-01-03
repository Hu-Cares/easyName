/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service.impl;

import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.dao.MallShopMapper;
import ltd.itema.mall.dao.MallUserMapper;
import ltd.itema.mall.dao.ItemaMallCouponMapper;
import ltd.itema.mall.dao.ItemaMallUserCouponRecordMapper;
import ltd.itema.mall.entity.ItemaMallCoupon;
import ltd.itema.mall.entity.ItemaMallUserCouponRecord;
import ltd.itema.mall.entity.MallShop;
import ltd.itema.mall.entity.MallUser;
import ltd.itema.mall.util.*;
import ltd.itema.mall.entity.*;
import ltd.itema.mall.service.ItemaMallUserService;
import ltd.itema.mall.util.*;
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
        MallShop registerShop = new MallShop();
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
              MallShop shop=mallShopMapper.selectByLoginName(loginName);  //从商家中获取店铺ID和店铺名以及姓名
              httpSession.setAttribute(Constants.MALL_SHOP_SESSION_KEY, shop);
            }
            ItemaMallUserVO itemaMallUserVO = new ItemaMallUserVO();
            BeanUtil.copyProperties(user, itemaMallUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, itemaMallUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public ItemaMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        ItemaMallUserVO userTemp = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
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
                ItemaMallUserVO itemaMallUserVO = new ItemaMallUserVO();
                userFromDB = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(userFromDB, itemaMallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, itemaMallUserVO);
                return itemaMallUserVO;
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
