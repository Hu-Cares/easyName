package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.ItemaException;
import ltd.newbee.mall.controller.vo.ItemaMallCouponVO;
import ltd.newbee.mall.controller.vo.ItemaMallMyCouponVO;
import ltd.newbee.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.newbee.mall.dao.ItemaMallCouponMapper;
import ltd.newbee.mall.dao.ItemaGoodsMapper;
import ltd.newbee.mall.dao.ItemaMallUserCouponRecordMapper;
import ltd.newbee.mall.entity.ItemaMallCoupon;
import ltd.newbee.mall.entity.ItemaMallGoods;
import ltd.newbee.mall.entity.ItemaMallUserCouponRecord;
import ltd.newbee.mall.service.ItemaMallCouponService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemaMallCouponServiceImpl implements ItemaMallCouponService {

    @Autowired
    private ItemaMallCouponMapper itemaMallCouponMapper;

    @Autowired
    private ItemaMallUserCouponRecordMapper itemaMallUserCouponRecordMapper;

    @Autowired
    private ItemaGoodsMapper itemaGoodsMapper;

    @Override
    public PageResult getCouponPage(PageQueryUtil pageUtil) {
        List<ItemaMallCoupon> carousels = itemaMallCouponMapper.findCouponlList(pageUtil);
        int total = itemaMallCouponMapper.getTotalCoupons(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public boolean saveCoupon(ItemaMallCoupon itemaMallCoupon) {
        return itemaMallCouponMapper.insertSelective(itemaMallCoupon) > 0;
    }

    @Override
    public boolean updateCoupon(ItemaMallCoupon itemaMallCoupon) {
        return itemaMallCouponMapper.updateByPrimaryKeySelective(itemaMallCoupon) > 0;
    }

    @Override
    public ItemaMallCoupon getCouponById(Long id) {
        return itemaMallCouponMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteCouponById(Long id) {
        return itemaMallCouponMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<ItemaMallCouponVO> selectAvailableCoupon(Long userId) {
        List<ItemaMallCoupon> coupons = itemaMallCouponMapper.selectAvailableCoupon();
        List<ItemaMallCouponVO> couponVOS = BeanUtil.copyList(coupons, ItemaMallCouponVO.class);
        for (ItemaMallCouponVO couponVO : couponVOS) {
            if (userId != null) {
                int num = itemaMallUserCouponRecordMapper.getUserCouponCount(userId, couponVO.getCouponId());
                if (num > 0) {
                    couponVO.setHasReceived(true);
                }
            }
            if (couponVO.getCouponTotal() != 0) {
                int count = itemaMallUserCouponRecordMapper.getCouponCount(couponVO.getCouponId());
                if (count >= couponVO.getCouponTotal()) {
                    couponVO.setSaleOut(true);
                }
            }
        }
        return couponVOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCouponUser(Long couponId, Long userId) {
        ItemaMallCoupon itemaMallCoupon = itemaMallCouponMapper.selectByPrimaryKey(couponId);
        if (itemaMallCoupon.getCouponLimit() != 0) {
            int num = itemaMallUserCouponRecordMapper.getUserCouponCount(userId, couponId);
            if (num != 0) {
                throw new ItemaException("优惠卷已经领过了,无法再次领取！");
            }
        }
        if (itemaMallCoupon.getCouponTotal() != 0) {
            int count = itemaMallUserCouponRecordMapper.getCouponCount(couponId);
            if (count >= itemaMallCoupon.getCouponTotal()) {
                throw new ItemaException("优惠卷已经领完了！");
            }
            if (itemaMallCouponMapper.reduceCouponTotal(couponId) <= 0) {
                throw new ItemaException("优惠卷领取失败！");
            }
        }
        ItemaMallUserCouponRecord couponUser = new ItemaMallUserCouponRecord();
        couponUser.setUserId(userId);
        couponUser.setCouponId(couponId);
        return itemaMallUserCouponRecordMapper.insertSelective(couponUser) > 0;
    }

    @Override
    public List<ItemaMallCouponVO> selectMyCoupons(Long userId) {
        List<ItemaMallUserCouponRecord> coupons = itemaMallUserCouponRecordMapper.selectMyCoupons(userId);
        List<ItemaMallCouponVO> couponVOS = new ArrayList<>();
        for (ItemaMallUserCouponRecord couponUser : coupons) {
            ItemaMallCoupon itemaMallCoupon = itemaMallCouponMapper.selectByPrimaryKey(couponUser.getCouponId());
            ItemaMallCouponVO newBeeMallCouponVO = new ItemaMallCouponVO();
            BeanUtil.copyProperties(itemaMallCoupon, newBeeMallCouponVO);
            newBeeMallCouponVO.setCouponUserId(couponUser.getCouponUserId());
            newBeeMallCouponVO.setUsed(couponUser.getUsedTime() != null);
            couponVOS.add(newBeeMallCouponVO);
        }
        return couponVOS;
    }

    @Override
    public List<ItemaMallMyCouponVO> selectOrderCanUseCoupons(List<ItemaMallShoppingCartItemVO> myShoppingCartItems, int priceTotal, Long userId) {
        List<ItemaMallUserCouponRecord> couponUsers = itemaMallUserCouponRecordMapper.selectMyAvailableCoupons(userId);
        List<ItemaMallMyCouponVO> myCouponVOS = BeanUtil.copyList(couponUsers, ItemaMallMyCouponVO.class);
        List<Long> couponIds = couponUsers.stream().map(ItemaMallUserCouponRecord::getCouponId).collect(Collectors.toList());
        if (!couponIds.isEmpty()) {
            List<ItemaMallCoupon> coupons = itemaMallCouponMapper.selectByIds(couponIds);
            for (ItemaMallCoupon coupon : coupons) {
                for (ItemaMallMyCouponVO myCouponVO : myCouponVOS) {
                    if (coupon.getCouponId().equals(myCouponVO.getCouponId())) {
                        myCouponVO.setName(coupon.getCouponName());
                        myCouponVO.setCouponDesc(coupon.getCouponDesc());
                        myCouponVO.setDiscount(coupon.getDiscount());
                        myCouponVO.setMin(coupon.getMin());
                        myCouponVO.setGoodsType(coupon.getGoodsType());
                        myCouponVO.setGoodsValue(coupon.getGoodsValue());
                        ZonedDateTime zonedDateTime = coupon.getCouponEndTime().atStartOfDay(ZoneId.systemDefault());
                        myCouponVO.setEndTime(Date.from(zonedDateTime.toInstant()));
                    }
                }
            }
        }
        return myCouponVOS.stream().filter(item -> {
            boolean b = false;
            if (item.getMin() <= priceTotal) {
                if (item.getGoodsType() == 1) { // 指定分类可用
                    String[] split = item.getGoodsValue().split(",");
                    List<Long> goodsValue = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList());
                    List<Long> goodsIds = myShoppingCartItems.stream().map(ItemaMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
                    List<ItemaMallGoods> goods = itemaGoodsMapper.selectByPrimaryKeys(goodsIds);
                    List<Long> categoryIds = goods.stream().map(ItemaMallGoods::getGoodsCategoryId).collect(Collectors.toList());
                    for (Long categoryId : categoryIds) {
                        if (goodsValue.contains(categoryId)) {
                            b = true;
                            break;
                        }
                    }
                } else if (item.getGoodsType() == 2) { // 指定商品可用
                    String[] split = item.getGoodsValue().split(",");
                    List<Long> goodsValue = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toList());
                    List<Long> goodsIds = myShoppingCartItems.stream().map(ItemaMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
                    for (Long goodsId : goodsIds) {
                        if (goodsValue.contains(goodsId)) {
                            b = true;
                            break;
                        }
                    }
                } else { // 全场通用
                    b = true;
                }
            }
            return b;
        }).sorted(Comparator.comparingInt(ItemaMallMyCouponVO::getDiscount)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCouponUser(Long couponUserId) {
        return itemaMallUserCouponRecordMapper.deleteByPrimaryKey(couponUserId) > 0;
    }

    @Override
    public void releaseCoupon(Long orderId) {
        ItemaMallUserCouponRecord itemaMallUserCouponRecord = itemaMallUserCouponRecordMapper.getUserCouponByOrderId(orderId);
        if (itemaMallUserCouponRecord == null) {
            return;
        }
        itemaMallUserCouponRecord.setUseStatus((byte) 0);
        itemaMallUserCouponRecord.setUpdateTime(new Date());
        itemaMallUserCouponRecordMapper.updateByPrimaryKey(itemaMallUserCouponRecord);
    }
}
