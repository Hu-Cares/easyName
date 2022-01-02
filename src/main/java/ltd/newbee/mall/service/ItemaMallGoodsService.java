 
package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.ItemaMallGoods;
import ltd.newbee.mall.entity.ItemaMallshopGoods;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

public interface ItemaMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(ItemaMallGoods goods);
    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String shopSaveNewBeeMallGoods(ItemaMallshopGoods goods);
    /**
     * 批量新增商品数据
     *
     * @param itemaMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<ItemaMallGoods> itemaMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(ItemaMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    ItemaMallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 商家获取商品详情
     *
     * @param id
     * @return
     */
    ItemaMallshopGoods getNewBeeMallShopGoodsById(Long id);
    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);
}
