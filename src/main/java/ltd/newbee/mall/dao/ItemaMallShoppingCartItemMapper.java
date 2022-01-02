 
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallShoppingCartItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(ItemaMallShoppingCartItem record);

    int insertSelective(ItemaMallShoppingCartItem record);

    ItemaMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    ItemaMallShoppingCartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    List<ItemaMallShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

    int selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(ItemaMallShoppingCartItem record);

    int updateByPrimaryKey(ItemaMallShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}