package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ItemaException;
import ltd.newbee.mall.controller.vo.ExposerVO;
import ltd.newbee.mall.controller.vo.ItemaMallSeckillGoodsVO;
import ltd.newbee.mall.controller.vo.ItemaUserVO;
import ltd.newbee.mall.controller.vo.SeckillSuccessVO;
import ltd.newbee.mall.dao.ItemaGoodsMapper;
import ltd.newbee.mall.entity.ItemaMallGoods;
import ltd.newbee.mall.entity.ItemaMallSeckill;
import ltd.newbee.mall.redis.RedisCache;
import ltd.newbee.mall.service.ItemaMallSeckillService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.MD5Util;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class SecKillController {

    @Autowired
    private ItemaMallSeckillService itemaMallSeckillService;

    @Autowired
    private ItemaGoodsMapper itemaGoodsMapper;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/seckill")
    public String seckillIndex() {
        return "mall/seckill-list";
    }

    /**
     * 获取服务器时间
     *
     * @return result
     */
    @ResponseBody
    @GetMapping("/seckill/time/now")
    public Result getTimeNow() {
        return ResultGenerator.genSuccessResult(new Date().getTime());
    }

    /**
     * 判断秒杀商品虚拟库存是否足够
     *
     * @param seckillId 秒杀ID
     * @return result
     */
    @ResponseBody
    @PostMapping("/seckill/{seckillId}/checkStock")
    public Result seckillCheckStock(@PathVariable Long seckillId) {
        Integer stock = redisCache.getCacheObject(Constants.SECKILL_GOODS_STOCK_KEY + seckillId);
        if (stock == null || stock < 0) {
            return ResultGenerator.genFailResult("秒杀商品库存不足");
        }
        // redis虚拟库存大于等于0时，可以执行秒杀
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 获取秒杀链接
     *
     * @param seckillId 秒杀商品ID
     * @return result
     */
    @ResponseBody
    @PostMapping("/seckill/{seckillId}/exposer")
    public Result exposerUrl(@PathVariable Long seckillId) {
        ExposerVO exposerVO = itemaMallSeckillService.exposerUrl(seckillId);
        return ResultGenerator.genSuccessResult(exposerVO);
    }

    /**
     * 使用限流注解进行接口限流操作
     *
     * @param seckillId 秒杀ID
     * @param userId    用户ID
     * @param md5       秒杀链接的MD5加密信息
     * @return result
     */
    @ResponseBody
    @PostMapping(value = "/seckillExecution/{seckillId}/{userId}/{md5}")
    public Result execute(@PathVariable Long seckillId,
                          @PathVariable Long userId,
                          @PathVariable String md5) {
        // 判断md5信息是否合法
        if (md5 == null || userId == null || !md5.equals(MD5Util.MD5Encode(seckillId.toString(), Constants.UTF_ENCODING))) {
            throw new ItemaException("秒杀商品不存在");
        }
        SeckillSuccessVO seckillSuccessVO = itemaMallSeckillService.executeSeckill(seckillId, userId);
        return ResultGenerator.genSuccessResult(seckillSuccessVO);
    }

    @GetMapping("/seckill/info/{seckillId}")
    public String seckillInfo(@PathVariable Long seckillId,
                              HttpServletRequest request,
                              HttpSession httpSession) {
        ItemaUserVO user = (ItemaUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        if (user != null) {
            request.setAttribute("userId", user.getUserId());
        }
        request.setAttribute("seckillId", seckillId);
        return "mall/seckill-detail";
    }

    @GetMapping("/seckill/list")
    @ResponseBody
    public Result secondKillGoodsList() {
        // 直接返回配置的秒杀商品列表
        // 不返回商品id，每配置一条秒杀数据，就生成一个唯一的秒杀id和发起秒杀的事件id，根据秒杀id去访问详情页
        List<ItemaMallSeckillGoodsVO> itemaMallSeckillGoodsVOS = redisCache.getCacheObject(Constants.SECKILL_GOODS_LIST);
        if (itemaMallSeckillGoodsVOS == null) {
            List<ItemaMallSeckill> list = itemaMallSeckillService.getHomeSeckillPage();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
            itemaMallSeckillGoodsVOS = list.stream().map(newBeeMallSeckill -> {
                ItemaMallSeckillGoodsVO itemaMallSeckillGoodsVO = new ItemaMallSeckillGoodsVO();
                BeanUtil.copyProperties(newBeeMallSeckill, itemaMallSeckillGoodsVO);
                ItemaMallGoods itemaMallGoods = itemaGoodsMapper.selectByPrimaryKey(newBeeMallSeckill.getGoodsId());
                if (itemaMallGoods == null) {
                    return null;
                }
                itemaMallSeckillGoodsVO.setGoodsName(itemaMallGoods.getGoodsName());
                itemaMallSeckillGoodsVO.setGoodsCoverImg(itemaMallGoods.getGoodsCoverImg());
                itemaMallSeckillGoodsVO.setSellingPrice(itemaMallGoods.getSellingPrice());
                Date seckillBegin = itemaMallSeckillGoodsVO.getSeckillBegin();
                Date seckillEnd = itemaMallSeckillGoodsVO.getSeckillEnd();
                String formatBegin = sdf.format(seckillBegin);
                String formatEnd = sdf.format(seckillEnd);
                itemaMallSeckillGoodsVO.setSeckillBeginTime(formatBegin);
                itemaMallSeckillGoodsVO.setSeckillEndTime(formatEnd);
                return itemaMallSeckillGoodsVO;
            }).filter(itemaMallSeckillGoodsVO -> itemaMallSeckillGoodsVO != null).collect(Collectors.toList());
            redisCache.setCacheObject(Constants.SECKILL_GOODS_LIST, itemaMallSeckillGoodsVOS, 60 * 60 * 100, TimeUnit.SECONDS);
        }
        return ResultGenerator.genSuccessResult(itemaMallSeckillGoodsVOS);
    }

    @GetMapping("/seckill/{seckillId}")
    @ResponseBody
    public Result seckillGoodsDetail(@PathVariable Long seckillId) {
        // 返回秒杀商品详情VO，如果秒杀时间未到，不允许访问详情页，也不允许返回数据，参数为秒杀id
        // 根据返回的数据解析出秒杀的事件id，发起秒杀
        // 不访问详情页不会获取到秒杀的事件id，不然容易被猜到url路径从而直接发起秒杀请求
        ItemaMallSeckillGoodsVO itemaMallSeckillGoodsVO = redisCache.getCacheObject(Constants.SECKILL_GOODS_DETAIL + seckillId);
        if (itemaMallSeckillGoodsVO == null) {
            ItemaMallSeckill itemaMallSeckill = itemaMallSeckillService.getSeckillById(seckillId);
            if (!itemaMallSeckill.getSeckillStatus()) {
                return ResultGenerator.genFailResult("秒杀商品已下架");
            }
            itemaMallSeckillGoodsVO = new ItemaMallSeckillGoodsVO();
            BeanUtil.copyProperties(itemaMallSeckill, itemaMallSeckillGoodsVO);
            ItemaMallGoods itemaMallGoods = itemaGoodsMapper.selectByPrimaryKey(itemaMallSeckill.getGoodsId());
            itemaMallSeckillGoodsVO.setGoodsName(itemaMallGoods.getGoodsName());
            itemaMallSeckillGoodsVO.setGoodsIntro(itemaMallGoods.getGoodsIntro());
            itemaMallSeckillGoodsVO.setGoodsDetailContent(itemaMallGoods.getGoodsDetailContent());
            itemaMallSeckillGoodsVO.setGoodsCoverImg(itemaMallGoods.getGoodsCoverImg());
            itemaMallSeckillGoodsVO.setSellingPrice(itemaMallGoods.getSellingPrice());
            Date seckillBegin = itemaMallSeckillGoodsVO.getSeckillBegin();
            Date seckillEnd = itemaMallSeckillGoodsVO.getSeckillEnd();
            itemaMallSeckillGoodsVO.setStartDate(seckillBegin.getTime());
            itemaMallSeckillGoodsVO.setEndDate(seckillEnd.getTime());
            redisCache.setCacheObject(Constants.SECKILL_GOODS_DETAIL + seckillId, itemaMallSeckillGoodsVO);
        }
        return ResultGenerator.genSuccessResult(itemaMallSeckillGoodsVO);
    }

}
