 
package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.newbee.mall.dao.ItemaGoodsMapper;
import ltd.newbee.mall.dao.ItemaMallShoppingCartItemMapper;
import ltd.newbee.mall.entity.ItemaMallGoods;
import ltd.newbee.mall.entity.ItemaMallShoppingCartItem;
import ltd.newbee.mall.service.ItemaMallShoppingCartService;
import ltd.newbee.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemaMallShoppingCartServiceImpl implements ItemaMallShoppingCartService {

    @Autowired
    private ItemaMallShoppingCartItemMapper itemaMallShoppingCartItemMapper;

    @Autowired
    private ItemaGoodsMapper itemaGoodsMapper;

    @Override
    public String saveNewBeeMallCartItem(ItemaMallShoppingCartItem itemaMallShoppingCartItem) {
        ItemaMallShoppingCartItem temp = itemaMallShoppingCartItemMapper.selectByUserIdAndGoodsId(itemaMallShoppingCartItem.getUserId(), itemaMallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(itemaMallShoppingCartItem.getGoodsCount());
            return updateNewBeeMallCartItem(temp);
        }
        ItemaMallGoods itemaMallGoods = itemaGoodsMapper.selectByPrimaryKey(itemaMallShoppingCartItem.getGoodsId());
        //商品为空
        if (itemaMallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = itemaMallShoppingCartItemMapper.selectCountByUserId(itemaMallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (itemaMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (itemaMallShoppingCartItemMapper.insertSelective(itemaMallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNewBeeMallCartItem(ItemaMallShoppingCartItem itemaMallShoppingCartItem) {
        ItemaMallShoppingCartItem itemaMallShoppingCartItemUpdate = itemaMallShoppingCartItemMapper.selectByPrimaryKey(itemaMallShoppingCartItem.getCartItemId());
        if (itemaMallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (itemaMallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        // 数量相同不会进行修改
        if (itemaMallShoppingCartItemUpdate.getGoodsCount().equals(itemaMallShoppingCartItem.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        // userId不同不能修改
        if (!itemaMallShoppingCartItem.getUserId().equals(itemaMallShoppingCartItemUpdate.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        itemaMallShoppingCartItemUpdate.setGoodsCount(itemaMallShoppingCartItem.getGoodsCount());
        itemaMallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (itemaMallShoppingCartItemMapper.updateByPrimaryKeySelective(itemaMallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public ItemaMallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
        return itemaMallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        ItemaMallShoppingCartItem itemaMallShoppingCartItem = itemaMallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (itemaMallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(itemaMallShoppingCartItem.getUserId())) {
            return false;
        }
        return itemaMallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<ItemaMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
        List<ItemaMallShoppingCartItemVO> itemaMallShoppingCartItemVOS = new ArrayList<>();
        List<ItemaMallShoppingCartItem> itemaMallShoppingCartItems = itemaMallShoppingCartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(itemaMallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = itemaMallShoppingCartItems.stream().map(ItemaMallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<ItemaMallGoods> itemaMallGoods = itemaGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, ItemaMallGoods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(itemaMallGoods)) {
                newBeeMallGoodsMap = itemaMallGoods.stream().collect(Collectors.toMap(ItemaMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (ItemaMallShoppingCartItem itemaMallShoppingCartItem : itemaMallShoppingCartItems) {
                ItemaMallShoppingCartItemVO itemaMallShoppingCartItemVO = new ItemaMallShoppingCartItemVO();
                BeanUtil.copyProperties(itemaMallShoppingCartItem, itemaMallShoppingCartItemVO);
                if (newBeeMallGoodsMap.containsKey(itemaMallShoppingCartItem.getGoodsId())) {
                    ItemaMallGoods itemaMallGoodsTemp = newBeeMallGoodsMap.get(itemaMallShoppingCartItem.getGoodsId());
                    itemaMallShoppingCartItemVO.setGoodsCoverImg(itemaMallGoodsTemp.getGoodsCoverImg());
                    String goodsName = itemaMallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    itemaMallShoppingCartItemVO.setGoodsName(goodsName);
                    itemaMallShoppingCartItemVO.setSellingPrice(itemaMallGoodsTemp.getSellingPrice());
                    itemaMallShoppingCartItemVOS.add(itemaMallShoppingCartItemVO);
                }
            }
        }
        return itemaMallShoppingCartItemVOS;
    }
}
