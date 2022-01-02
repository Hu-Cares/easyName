 
package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.newbee.mall.entity.ItemaMallShoppingCartItem;

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
