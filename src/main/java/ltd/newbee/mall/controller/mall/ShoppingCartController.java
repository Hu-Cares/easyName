 
package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.ItemaMallMyCouponVO;
import ltd.newbee.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.newbee.mall.controller.vo.ItemaUserVO;
import ltd.newbee.mall.entity.ItemaMallShoppingCartItem;
import ltd.newbee.mall.service.ItemaMallCouponService;
import ltd.newbee.mall.service.ItemaMallShoppingCartService;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
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
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
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
    public Result saveNewBeeMallShoppingCartItem(@RequestBody ItemaMallShoppingCartItem itemaMallShoppingCartItem,
                                                 HttpSession httpSession) {
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        itemaMallShoppingCartItem.setUserId(user.getUserId());
        String saveResult = itemaMallShoppingCartService.saveNewBeeMallCartItem(itemaMallShoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@RequestBody ItemaMallShoppingCartItem itemaMallShoppingCartItem,
                                                   HttpSession httpSession) {
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        itemaMallShoppingCartItem.setUserId(user.getUserId());
        String updateResult = itemaMallShoppingCartService.updateNewBeeMallCartItem(itemaMallShoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
    @ResponseBody
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                   HttpSession httpSession) {
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        Boolean deleteResult = itemaMallShoppingCartService.deleteById(newBeeMallShoppingCartItemId, user.getUserId());
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
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
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
