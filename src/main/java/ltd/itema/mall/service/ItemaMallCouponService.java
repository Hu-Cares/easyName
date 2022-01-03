package ltd.itema.mall.service;

import ltd.itema.mall.controller.vo.ItemaMallCouponVO;
import ltd.itema.mall.controller.vo.ItemaMallMyCouponVO;
import ltd.itema.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.itema.mall.entity.ItemaMallCoupon;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;

import java.util.List;

public interface ItemaMallCouponService {

    PageResult getCouponPage(PageQueryUtil pageUtil);

    boolean saveCoupon(ItemaMallCoupon itemaMallCoupon);

    boolean updateCoupon(ItemaMallCoupon itemaMallCoupon);

    ItemaMallCoupon getCouponById(Long id);

    boolean deleteCouponById(Long id);

    /**
     * 查询可用优惠卷
     *
     * @param userId
     * @return
     */
    List<ItemaMallCouponVO> selectAvailableCoupon(Long userId);

    /**
     * 用户领取优惠劵
     *
     * @param couponId 优惠劵ID
     * @param userId   用户ID
     * @return boolean
     */
    boolean saveCouponUser(Long couponId, Long userId);

    /**
     * 查询我的优惠卷
     *
     * @param userId 用户ID
     * @return
     */
    List<ItemaMallCouponVO> selectMyCoupons(Long userId);

    /**
     * 查询当前订单可用的优惠卷
     *
     * @param myShoppingCartItems
     * @param priceTotal
     * @param userId
     * @return
     */
    List<ItemaMallMyCouponVO> selectOrderCanUseCoupons(List<ItemaMallShoppingCartItemVO> myShoppingCartItems, int priceTotal, Long userId);

    /**
     * 删除用户优惠卷
     *
     * @param couponUserId
     * @return
     */
    boolean deleteCouponUser(Long couponUserId);

    /**
     * 回复未支付的优惠卷
     * @param orderId
     */
    void releaseCoupon(Long orderId);
}
