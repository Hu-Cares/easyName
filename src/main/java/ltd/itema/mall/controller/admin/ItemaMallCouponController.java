package ltd.itema.mall.controller.admin;

import ltd.itema.mall.entity.ItemaMallCoupon;
import ltd.itema.mall.service.ItemaMallCouponService;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class ItemaMallCouponController {

    @Autowired
    private ItemaMallCouponService itemaMallCouponService;

    @GetMapping("/coupon")
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_coupon");
        return "admin/newbee_mall_coupon";
    }

    @ResponseBody
    @GetMapping("/coupon/list")
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((CharSequence) params.get("page")) || StringUtils.isEmpty((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(itemaMallCouponService.getCouponPage(pageUtil));
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/coupon/save")
    public Result save(@RequestBody ItemaMallCoupon itemaMallCoupon) {
        return ResultGenerator.genDmlResult(itemaMallCouponService.saveCoupon(itemaMallCoupon));
    }

    /**
     * 更新
     */
    @PostMapping("/coupon/update")
    @ResponseBody
    public Result update(@RequestBody ItemaMallCoupon itemaMallCoupon) {
        itemaMallCoupon.setUpdateTime(new Date());
        return ResultGenerator.genDmlResult(itemaMallCouponService.updateCoupon(itemaMallCoupon));
    }

    /**
     * 详情
     */
    @GetMapping("/coupon/{id}")
    @ResponseBody
    public Result Info(@PathVariable("id") Long id) {
        ItemaMallCoupon itemaMallCoupon = itemaMallCouponService.getCouponById(id);
        return ResultGenerator.genSuccessResult(itemaMallCoupon);
    }

    /**
     * 删除
     */
    @DeleteMapping("/coupon/{id}")
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        return ResultGenerator.genDmlResult(itemaMallCouponService.deleteCouponById(id));
    }
}
