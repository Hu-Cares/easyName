 
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallOrder;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(ItemaMallOrder record);

    int insertSelective(ItemaMallOrder record);

    ItemaMallOrder selectByPrimaryKey(Long orderId);

    ItemaMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(ItemaMallOrder record);

    int updateByPrimaryKey(ItemaMallOrder record);

    List<ItemaMallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    List<ItemaMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);

    List<ItemaMallOrder> selectPrePayOrders();
}
