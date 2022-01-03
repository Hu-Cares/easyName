/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service.impl;

import ltd.itema.mall.controller.vo.ItemaMallSearchGoodsVO;
import ltd.itema.mall.entity.*;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ItemaMallCategoryLevelEnum;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.dao.GoodsCategoryMapper;
import ltd.itema.mall.dao.ItemaMallGoodsMapper;
import ltd.itema.mall.dao.ItemaMallOrderMapper;
import ltd.itema.mall.entity.*;
import ltd.itema.mall.service.ItemaMallGoodsService;
import ltd.itema.mall.util.BeanUtil;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ItemaMallGoodsServiceImpl implements ItemaMallGoodsService {

    @Autowired
    private ItemaMallGoodsMapper goodsMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private ItemaMallOrderMapper itemaMallOrderMapper;
    @Autowired
    private HttpServletRequest request;
    @Override
    public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
        List<ItemaMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsList(pageUtil);
        MallShop mallshop=(MallShop)request.getSession().getAttribute(Constants.MALL_SHOP_SESSION_KEY);
        if (mallshop!=null&&null == request.getSession().getAttribute("loginUser")) {
            Iterator<ItemaMallGoods> it = goodsList.iterator();
            while(it.hasNext()){
                ItemaMallGoods x = it.next();
                if((x.getShopId())!=(mallshop.getShopId())){
                    it.remove();
                }
            }
        }
        int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveNewBeeMallGoods(ItemaMallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    @Override
    public String shopSaveNewBeeMallGoods(ItemaMallshopGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.shopInsertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveNewBeeMallGoods(List<ItemaMallGoods> itemaMallGoodsList) {
        if (!CollectionUtils.isEmpty(itemaMallGoodsList)) {
            goodsMapper.batchInsert(itemaMallGoodsList);
        }
    }

    @Override
    public String updateNewBeeMallGoods(ItemaMallGoods goods) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(goods.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        ItemaMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        ItemaMallGoods temp2 = goodsMapper.selectByCategoryIdAndName(goods.getGoodsName(), goods.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(goods.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public ItemaMallGoods getNewBeeMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<String> getNewBeeMallCommentById(Long id){
     List<ItemaMallOrder> itemaMallOrders = itemaMallOrderMapper.selectByGoodsId(id);
     List<String> Comments = new ArrayList<String>();
     for(ItemaMallOrder itemaMallOrder : itemaMallOrders){
         Comments.add(itemaMallOrder.getComment());
     }
     return Comments;
    }
    //商家找寻
    @Override
    public ItemaMallshopGoods getNewBeeMallShopGoodsById(Long id) {
        return goodsMapper.shopSelectByPrimaryKey(id);
    } //商家找寻
    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil) {
        List<ItemaMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListBySearch(pageUtil);
        MallShop mallshop=(MallShop)request.getSession().getAttribute(Constants.MALL_SHOP_SESSION_KEY);
        if (mallshop!=null&&null == request.getSession().getAttribute("loginUser")) {
            Iterator<ItemaMallGoods> it = goodsList.iterator();
            while(it.hasNext()){
                ItemaMallGoods x = it.next();
                if((x.getShopId())!=(mallshop.getShopId())){
                    it.remove();
                }
            }
        }
        int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
        List<ItemaMallSearchGoodsVO> itemaMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            itemaMallSearchGoodsVOS = BeanUtil.copyList(goodsList, ItemaMallSearchGoodsVO.class);
            for (ItemaMallSearchGoodsVO itemaMallSearchGoodsVO : itemaMallSearchGoodsVOS) {
                String goodsName = itemaMallSearchGoodsVO.getGoodsName();
                String goodsIntro = itemaMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    itemaMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    itemaMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(itemaMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
