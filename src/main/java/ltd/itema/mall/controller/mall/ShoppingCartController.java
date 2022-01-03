/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.controller.mall;

import ltd.itema.mall.controller.vo.ItemaMallMyCouponVO;
import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.itema.mall.entity.ItemaMallShoppingCartItem;
import ltd.itema.mall.service.ItemaMallCouponService;
import ltd.itema.mall.service.ItemaMallShoppingCartService;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShoppingCartController {

    @Resource
    private ItemaMallShoppingCartService itemaMallShoppingCartService;

    @Autowired
    private ItemaMallCouponService itemaMallCouponService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,
                               HttpSession httpSession) {
        ItemaMallUserVO user = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        int itemsTotal = 0;
        int priceTotal = 0;
        List<ItemaMallShoppingCartItemVO> myShoppingCartItems = itemaMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物项总数
            itemsTotal = myShoppingCartItems.stream().mapToInt(ItemaMallShoppingCartItemVO::getGoodsCount).sum();
            if (itemsTotal < 1) {
                return "error/error_5xx";
            }
            //总价
            for (ItemaMallShoppingCartItemVO itemaMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += itemaMallShoppingCartItemVO.getGoodsCount() * itemaMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        request.setAttribute("itemsTotal", itemsTotal);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveItemaShoppingCartItem(@RequestBody ItemaMallShoppingCartItem itemaMallShoppingCartItem,
                                                 HttpSession httpSession) {
        ItemaMallUserVO user = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        itemaMallShoppingCartItem.setUserId(user.getUserId());
        String saveResult = itemaMallShoppingCartService.saveItemaMallCartItem(itemaMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateItemaMallShoppingCartItem(@RequestBody ItemaMallShoppingCartItem itemaMallShoppingCartItem,
                                                   HttpSession httpSession) {
        ItemaMallUserVO user = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        itemaMallShoppingCartItem.setUserId(user.getUserId());
        String updateResult = itemaMallShoppingCartService.updateItemaMallCartItem(itemaMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{itemaMallShoppingCartItemId}")
    @ResponseBody
    public Result updateItemaMallShoppingCartItem(@PathVariable("itemaMallShoppingCartItemId") Long itemaMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        ItemaMallUserVO user = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = itemaMallShoppingCartService.deleteById(itemaMallShoppingCartItemId, user.getUserId());
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,
                             HttpSession httpSession) {
        int priceTotal = 0;
        ItemaMallUserVO user = (ItemaMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<ItemaMallShoppingCartItemVO> myShoppingCartItems = itemaMallShoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        } else {
            //总价
            for (ItemaMallShoppingCartItemVO itemaMallShoppingCartItemVO : myShoppingCartItems) {
                priceTotal += itemaMallShoppingCartItemVO.getGoodsCount() * itemaMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                return "error/error_5xx";
            }
        }
        List<ItemaMallMyCouponVO> myCouponVOS = itemaMallCouponService.selectOrderCanUseCoupons(myShoppingCartItems, priceTotal, user.getUserId());
        request.setAttribute("coupons", myCouponVOS);
        request.setAttribute("priceTotal", priceTotal);
        request.setAttribute("myShoppingCartItems", myShoppingCartItems);
        return "mall/order-settle";
    }
}
