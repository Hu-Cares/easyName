package ltd.itema.mall.dao;

import ltd.itema.mall.entity.ItemaMallUserCouponRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallUserCouponRecordMapper {
    int deleteByPrimaryKey(Long couponUserId);

    int insert(ItemaMallUserCouponRecord record);

    int insertSelective(ItemaMallUserCouponRecord record);

    ItemaMallUserCouponRecord selectByPrimaryKey(Long couponUserId);

    int updateByPrimaryKeySelective(ItemaMallUserCouponRecord record);

    int updateByPrimaryKey(ItemaMallUserCouponRecord record);

    int getUserCouponCount(Long userId, Long couponId);

    int getCouponCount(Long couponId);

    List<ItemaMallUserCouponRecord> selectMyCoupons(Long userId);

    List<ItemaMallUserCouponRecord> selectMyAvailableCoupons(Long userId);

    ItemaMallUserCouponRecord getUserCouponByOrderId(Long orderId);
}
