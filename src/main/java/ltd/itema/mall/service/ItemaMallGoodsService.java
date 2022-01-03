/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service;

import ltd.itema.mall.entity.ItemaMallshopGoods;
import ltd.itema.mall.entity.ItemaMallGoods;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;

import java.util.List;

public interface ItemaMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getItemaMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveItemaMallGoods(ItemaMallGoods goods);
    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String shopSaveItemaMallGoods(ItemaMallshopGoods goods);
    /**
     * 批量新增商品数据
     *
     * @param itemaMallGoodsList
     * @return
     */
    void batchSaveItemaMallGoods(List<ItemaMallGoods> itemaMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateItemaMallGoods(ItemaMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    ItemaMallGoods getItemaMallGoodsById(Long id);

    List<String> getItemaMallCommentById(Long id);

    /**
     * 商家获取商品详情
     *
     * @param id
     * @return
     */
    ItemaMallshopGoods getItemaMallShopGoodsById(Long id);
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
    PageResult searchItemaMallGoods(PageQueryUtil pageUtil);
}
