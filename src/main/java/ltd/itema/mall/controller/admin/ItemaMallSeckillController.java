package ltd.itema.mall.controller.admin;

import ltd.itema.mall.common.Constants;
import ltd.itema.mall.entity.ItemaMallSeckill;
import ltd.itema.mall.redis.RedisCache;
import ltd.itema.mall.service.ItemaMallSeckillService;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class ItemaMallSeckillController {

    @Autowired
    private ItemaMallSeckillService itemaMallSeckillService;
    @Autowired
    private RedisCache redisCache;

    @GetMapping("/seckill")
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_seckill");
        return "admin/newbee_mall_seckill";
    }

    @ResponseBody
    @GetMapping("/seckill/list")
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty((CharSequence) params.get("page")) || StringUtils.isEmpty((CharSequence) params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(itemaMallSeckillService.getSeckillPage(pageUtil));
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/seckill/save")
    public Result save(@RequestBody ItemaMallSeckill itemaMallSeckill) {
        if (itemaMallSeckill == null || itemaMallSeckill.getGoodsId() < 1 || itemaMallSeckill.getSeckillNum() < 1 || itemaMallSeckill.getSeckillPrice() < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        boolean result = itemaMallSeckillService.saveSeckill(itemaMallSeckill);
        if (result) {
            // 虚拟库存预热
            redisCache.setCacheObject(Constants.SECKILL_GOODS_STOCK_KEY + itemaMallSeckill.getSeckillId(), itemaMallSeckill.getSeckillNum());
        }
        return ResultGenerator.genDmlResult(result);
    }

    /**
     * 更新
     */
    @PostMapping("/seckill/update")
    @ResponseBody
    public Result update(@RequestBody ItemaMallSeckill itemaMallSeckill) {
        if (itemaMallSeckill == null || itemaMallSeckill.getSeckillId() == null || itemaMallSeckill.getGoodsId() < 1 || itemaMallSeckill.getSeckillNum() < 1 || itemaMallSeckill.getSeckillPrice() < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        boolean result = itemaMallSeckillService.updateSeckill(itemaMallSeckill);
        if (result) {
            // 虚拟库存预热
            redisCache.setCacheObject(Constants.SECKILL_GOODS_STOCK_KEY + itemaMallSeckill.getSeckillId(), itemaMallSeckill.getSeckillNum());
            redisCache.deleteObject(Constants.SECKILL_GOODS_DETAIL + itemaMallSeckill.getSeckillId());
            redisCache.deleteObject(Constants.SECKILL_GOODS_LIST);
        }
        return ResultGenerator.genDmlResult(result);
    }

    /**
     * 详情
     */
    @GetMapping("/seckill/{id}")
    @ResponseBody
    public Result Info(@PathVariable("id") Long id) {
        ItemaMallSeckill itemaMallSeckill = itemaMallSeckillService.getSeckillById(id);
        return ResultGenerator.genSuccessResult(itemaMallSeckill);
    }

    /**
     * 删除
     */
    @DeleteMapping("/seckill/{id}")
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        redisCache.deleteObject(Constants.SECKILL_GOODS_DETAIL + id);
        redisCache.deleteObject(Constants.SECKILL_GOODS_LIST);
        return ResultGenerator.genDmlResult(itemaMallSeckillService.deleteSeckillById(id));
    }
}
