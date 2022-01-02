package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallCoupon;
import ltd.newbee.mall.util.PageQueryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallCouponMapper {
    int deleteByPrimaryKey(Long couponId);

    int deleteBatch(Integer[] couponIds);

    int insert(ItemaMallCoupon record);

    int insertSelective(ItemaMallCoupon record);

    ItemaMallCoupon selectByPrimaryKey(Long couponId);

    int updateByPrimaryKeySelective(ItemaMallCoupon record);

    int updateByPrimaryKey(ItemaMallCoupon record);

    List<ItemaMallCoupon> findCouponlList(PageQueryUtil pageUtil);

    int getTotalCoupons(PageQueryUtil pageUtil);

    List<ItemaMallCoupon> selectAvailableCoupon();

    int reduceCouponTotal(Long couponId);

    List<ItemaMallCoupon> selectByIds(List<Long> couponIds);

    List<ItemaMallCoupon> selectAvailableGiveCoupon();

}
