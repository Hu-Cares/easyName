/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.dao;

import ltd.itema.mall.entity.ItemaMallGoods;
import ltd.itema.mall.entity.ItemaMallshopGoods;
import ltd.itema.mall.entity.StockNumDTO;
import ltd.itema.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemaMallGoodsMapper {
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

    List<ItemaMallGoods> findItemaMallGoodsList(PageQueryUtil pageUtil);

    int getTotalItemaMallGoods(PageQueryUtil pageUtil);

    List<ItemaMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<ItemaMallGoods> findItemaMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalItemaMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("itemaMallGoodsList") List<ItemaMallGoods> itemaMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

    boolean addStock(Long goodsId, Integer goodsCount);
}
