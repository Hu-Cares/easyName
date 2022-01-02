 
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallOrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(ItemaMallOrderItem record);

    int insertSelective(ItemaMallOrderItem record);

    ItemaMallOrderItem selectByPrimaryKey(Long orderItemId);

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<ItemaMallOrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ids获取订单项列表
     *
     * @param orderIds
     * @return
     */
    List<ItemaMallOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量insert订单项数据
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<ItemaMallOrderItem> orderItems);

    int updateByPrimaryKeySelective(ItemaMallOrderItem record);

    int updateByPrimaryKey(ItemaMallOrderItem record);
}