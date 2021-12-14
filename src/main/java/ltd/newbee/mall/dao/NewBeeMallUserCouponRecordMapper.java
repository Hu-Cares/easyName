package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.NewBeeMallUserCouponRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NewBeeMallUserCouponRecordMapper {
    int deleteByPrimaryKey(Long couponUserId);

    int insert(NewBeeMallUserCouponRecord record);

    int insertSelective(NewBeeMallUserCouponRecord record);

    NewBeeMallUserCouponRecord selectByPrimaryKey(Long couponUserId);

    int updateByPrimaryKeySelective(NewBeeMallUserCouponRecord record);

    int updateByPrimaryKey(NewBeeMallUserCouponRecord record);

    int getUserCouponCount(Long userId, Long couponId);

    int getCouponCount(Long couponId);

    List<NewBeeMallUserCouponRecord> selectMyCoupons(Long userId);

    List<NewBeeMallUserCouponRecord> selectMyAvailableCoupons(Long userId);

    NewBeeMallUserCouponRecord getUserCouponByOrderId(Long orderId);
}
