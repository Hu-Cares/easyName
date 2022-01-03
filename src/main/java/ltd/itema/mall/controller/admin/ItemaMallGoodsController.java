/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.controller.admin;

import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ItemaMallCategoryLevelEnum;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.entity.GoodsCategory;
import ltd.itema.mall.entity.ItemaMallGoods;
import ltd.itema.mall.entity.MallShop;
import ltd.itema.mall.service.ItemaMallCategoryService;
import ltd.itema.mall.service.ItemaMallGoodsService;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class ItemaMallGoodsController {

    @Resource
    private ItemaMallGoodsService itemaMallGoodsService;
    @Resource
    private ItemaMallCategoryService itemaMallCategoryService;
    @Autowired
    private HttpServletRequest request;
    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "itema_mall_goods");
        return "admin/itema_mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), ItemaMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), ItemaMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/itema_mall_goods_edit";
            }
        }
        return "error/error_5xx";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        ItemaMallGoods itemaMallGoods = itemaMallGoodsService.getItemaMallGoodsById(goodsId);
        if (itemaMallGoods == null) {
            return "error/error_400";
        }
        if (itemaMallGoods.getGoodsCategoryId() > 0) {
            if (itemaMallGoods.getGoodsCategoryId() != null || itemaMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = itemaMallCategoryService.getGoodsCategoryById(itemaMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), ItemaMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = itemaMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), ItemaMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = itemaMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (itemaMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), ItemaMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), ItemaMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = itemaMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), ItemaMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", itemaMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/itema_mall_goods_edit";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((CharSequence) params.get("page")) || StringUtils.isEmpty((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(itemaMallGoodsService.getItemaMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody ItemaMallGoods itemaMallGoods) {
        if (StringUtils.isEmpty(itemaMallGoods.getGoodsName())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(itemaMallGoods.getTag())
                || Objects.isNull(itemaMallGoods.getOriginalPrice())
                || Objects.isNull(itemaMallGoods.getGoodsCategoryId())
                || Objects.isNull(itemaMallGoods.getSellingPrice())
                || Objects.isNull(itemaMallGoods.getStockNum())
                || Objects.isNull(itemaMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        MallShop mallshop=(MallShop)request.getSession().getAttribute(Constants.MALL_SHOP_SESSION_KEY);
        if (mallshop!=null&&null == request.getSession().getAttribute("loginUser")) {
            itemaMallGoods.setShopId(mallshop.getShopId());
        }
        String result = itemaMallGoodsService.saveItemaMallGoods(itemaMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody ItemaMallGoods itemaMallGoods) {
        if (Objects.isNull(itemaMallGoods.getGoodsId())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsName())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(itemaMallGoods.getTag())
                || Objects.isNull(itemaMallGoods.getOriginalPrice())
                || Objects.isNull(itemaMallGoods.getSellingPrice())
                || Objects.isNull(itemaMallGoods.getGoodsCategoryId())
                || Objects.isNull(itemaMallGoods.getStockNum())
                || Objects.isNull(itemaMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(itemaMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        MallShop mallshop=(MallShop)request.getSession().getAttribute(Constants.MALL_SHOP_SESSION_KEY);
        if (mallshop!=null&&null == request.getSession().getAttribute("loginUser")) {
            itemaMallGoods.setShopId(mallshop.getShopId());
        }
        String result = itemaMallGoodsService.updateItemaMallGoods(itemaMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        ItemaMallGoods goods = itemaMallGoodsService.getItemaMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (itemaMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}
