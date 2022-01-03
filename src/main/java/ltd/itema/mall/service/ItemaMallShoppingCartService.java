/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service;

import ltd.itema.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.itema.mall.entity.ItemaMallShoppingCartItem;

import java.util.List;

public interface ItemaMallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param itemaMallShoppingCartItem
     * @return
     */
    String saveNewBeeMallCartItem(ItemaMallShoppingCartItem itemaMallShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param itemaMallShoppingCartItem
     * @return
     */
    String updateNewBeeMallCartItem(ItemaMallShoppingCartItem itemaMallShoppingCartItem);

    /**
     * 获取购物项详情
     *
     * @param newBeeMallShoppingCartItemId
     * @return
     */
    ItemaMallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param newBeeMallUserId
     * @return
     */
    List<ItemaMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);
}
