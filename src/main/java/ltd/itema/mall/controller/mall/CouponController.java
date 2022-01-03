package ltd.itema.mall.controller.mall;

import ltd.itema.mall.controller.vo.ItemaMallCouponVO;
import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.service.ItemaMallCouponService;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CouponController {

    @Autowired
    private ItemaMallCouponService itemaMallCouponService;

    @GetMapping("/couponList")
    public String couponList(HttpServletRequest request, HttpSession session) {
        Long userId = null;
        if (session.getAttribute(Constants.MALL_USER_SESSION_KEY) != null) {
            userId = ((ItemaMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)).getUserId();
        }
        List<ItemaMallCouponVO> coupons = itemaMallCouponService.selectAvailableCoupon(userId);
        request.setAttribute("coupons", coupons);
        return "mall/coupon-list";
    }

    @GetMapping("/myCoupons")
    public String myCoupons(HttpServletRequest request, HttpSession session) {
        ItemaMallUserVO userVO = (ItemaMallUserVO) session.getAttribute(Constants.MALL_USER_SESSION_KEY);
        List<ItemaMallCouponVO> coupons = itemaMallCouponService.selectMyCoupons(userVO.getUserId());
        request.setAttribute("myCoupons", coupons);
        request.setAttribute("path", "myCoupons");
        return "mall/my-coupons";
    }

    @ResponseBody
    @PostMapping("coupon/{couponId}")
    public Result save(@PathVariable Long couponId, HttpSession session) {
        ItemaMallUserVO userVO = (ItemaMallUserVO) session.getAttribute(Constants.MALL_USER_SESSION_KEY);
        return ResultGenerator.genDmlResult(itemaMallCouponService.saveCouponUser(couponId, userVO.getUserId()));
    }

    @ResponseBody
    @DeleteMapping("coupon/{couponUserId}")
    public Result delete(@PathVariable Long couponUserId) {
        return ResultGenerator.genDmlResult(itemaMallCouponService.deleteCouponUser(couponUserId));
    }
}
