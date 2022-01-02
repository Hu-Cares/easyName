 
package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallGoods;
import ltd.newbee.mall.entity.ItemaMallshopGoods;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(ItemaMallGoods record);

    int insertSelective(ItemaMallGoods record);

    int shopInsertSelective(ItemaMallshopGoods record);

    ItemaMallGoods selectByPrimaryKey(Long goodsId);

    ItemaMallshopGoods shopSelectByPrimaryKey(Long goodsId); //商家找寻

    ItemaMallGoods selectByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    int updateByPrimaryKeySelective(ItemaMallGoods record);

    int updateByPrimaryKeyWithBLOBs(ItemaMallGoods record);

    int updateByPrimaryKey(ItemaMallGoods record);

    List<ItemaMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<ItemaMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<ItemaMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<ItemaMallGoods> itemaMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

    boolean addStock(Long goodsId, Integer goodsCount);
}
