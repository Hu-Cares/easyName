/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.controller.mall;

import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ItemaMallException;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.controller.vo.ItemaMallGoodsDetailVO;
import ltd.itema.mall.controller.vo.SearchPageCategoryVO;
import ltd.itema.mall.entity.ItemaMallGoods;
import ltd.itema.mall.service.ItemaMallCategoryService;
import ltd.itema.mall.service.ItemaMallGoodsService;
import ltd.itema.mall.util.BeanUtil;
import ltd.itema.mall.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private ItemaMallGoodsService itemaMallGoodsService;
    @Resource
    private ItemaMallCategoryService itemaMallCategoryService;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty((CharSequence) params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = itemaMallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", itemaMallGoodsService.searchNewBeeMallGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        ItemaMallGoods goods = itemaMallGoodsService.getNewBeeMallGoodsById(goodsId);
        List<String> Comments = itemaMallGoodsService.getNewBeeMallCommentById(goodsId);
        List<String> Commenttwo=new ArrayList<String>();
        int flag=0;
        for(String s:Comments){
            Commenttwo.add(s);
            flag++;
            if(flag==2) break;
        }
        if (goods == null) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        ItemaMallGoodsDetailVO goodsDetailVO = new ItemaMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        request.setAttribute("Commenttwo",Commenttwo);
        return "mall/detail";
    }
    @GetMapping("/goods/comment/{goodsId}")
    public String commentPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        ItemaMallGoods goods = itemaMallGoodsService.getNewBeeMallGoodsById(goodsId);
        List<String> Comments = itemaMallGoodsService.getNewBeeMallCommentById(goodsId);
        if (goods == null) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        ItemaMallGoodsDetailVO goodsDetailVO = new ItemaMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        request.setAttribute("Comments",Comments);
        return "mall/comments";
    }
}
