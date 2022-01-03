package ltd.itema.mall.service.impl;

import com.google.common.util.concurrent.RateLimiter;
import ltd.itema.mall.controller.vo.ExposerVO;
import ltd.itema.mall.controller.vo.ItemaMallSeckillGoodsVO;
import ltd.itema.mall.controller.vo.SeckillSuccessVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ItemaMallException;
import ltd.itema.mall.common.SeckillStatusEnum;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.dao.ItemaMallGoodsMapper;
import ltd.itema.mall.dao.ItemaMallSeckillMapper;
import ltd.itema.mall.dao.ItemaMallSeckillSuccessMapper;
import ltd.itema.mall.entity.ItemaMallSeckill;
import ltd.itema.mall.entity.ItemaMallSeckillSuccess;
import ltd.itema.mall.redis.RedisCache;
import ltd.itema.mall.service.ItemaMallSeckillService;
import ltd.itema.mall.util.MD5Util;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ItemaMallSeckillServiceImpl implements ItemaMallSeckillService {

    // 使用令牌桶RateLimiter 限流
    private static final RateLimiter rateLimiter = RateLimiter.create(10);

    @Autowired
    private ItemaMallSeckillMapper itemaMallSeckillMapper;

    @Autowired
    private ItemaMallSeckillSuccessMapper itemaMallSeckillSuccessMapper;

    @Autowired
    private ItemaMallGoodsMapper itemaMallGoodsMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public PageResult getSeckillPage(PageQueryUtil pageUtil) {
        List<ItemaMallSeckill> carousels = itemaMallSeckillMapper.findSeckillList(pageUtil);
        int total = itemaMallSeckillMapper.getTotalSeckills(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public boolean saveSeckill(ItemaMallSeckill itemaMallSeckill) {
        if (itemaMallGoodsMapper.selectByPrimaryKey(itemaMallSeckill.getGoodsId()) == null) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return itemaMallSeckillMapper.insertSelective(itemaMallSeckill) > 0;
    }

    @Override
    public boolean updateSeckill(ItemaMallSeckill itemaMallSeckill) {
        if (itemaMallGoodsMapper.selectByPrimaryKey(itemaMallSeckill.getGoodsId()) == null) {
            ItemaMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        ItemaMallSeckill temp = itemaMallSeckillMapper.selectByPrimaryKey(itemaMallSeckill.getSeckillId());
        if (temp == null) {
            ItemaMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        itemaMallSeckill.setUpdateTime(new Date());
        return itemaMallSeckillMapper.updateByPrimaryKeySelective(itemaMallSeckill) > 0;
    }

    @Override
    public ItemaMallSeckill getSeckillById(Long id) {
        return itemaMallSeckillMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteSeckillById(Long id) {
        return itemaMallSeckillMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<ItemaMallSeckill> getHomeSeckillPage() {
        return itemaMallSeckillMapper.findHomeSeckillList();
    }

    @Override
    public ExposerVO exposerUrl(Long seckillId) {
        ItemaMallSeckillGoodsVO itemaMallSeckillGoodsVO = redisCache.getCacheObject(Constants.SECKILL_GOODS_DETAIL + seckillId);
        Date startTime = itemaMallSeckillGoodsVO.getSeckillBegin();
        Date endTime = itemaMallSeckillGoodsVO.getSeckillEnd();
        // 系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new ExposerVO(SeckillStatusEnum.NOT_START, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 检查虚拟库存
        Integer stock = redisCache.getCacheObject(Constants.SECKILL_GOODS_STOCK_KEY + seckillId);
        if (stock == null || stock < 0) {
            return new ExposerVO(SeckillStatusEnum.STARTED_SHORTAGE_STOCK, seckillId);
        }
        // 加密
        String md5 = MD5Util.MD5Encode(seckillId.toString(), Constants.UTF_ENCODING);
        return new ExposerVO(SeckillStatusEnum.START, md5, seckillId);
    }

    @Override
    public SeckillSuccessVO executeSeckill(Long seckillId, Long userId) {
        // 判断能否在500毫秒内得到令牌，如果不能则立即返回false，不会阻塞程序
        if (!rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS)) {
            throw new ItemaMallException("秒杀失败");
        }
        // 判断用户是否购买过秒杀商品
        if (redisCache.containsCacheSet(Constants.SECKILL_SUCCESS_USER_ID + seckillId, userId)) {
            throw new ItemaMallException("您已经购买过秒杀商品，请勿重复购买");
        }
        // 更新秒杀商品虚拟库存
        Long stock = redisCache.luaDecrement(Constants.SECKILL_GOODS_STOCK_KEY + seckillId);
        if (stock < 0) {
            throw new ItemaMallException("秒杀商品已售空");
        }
        ItemaMallSeckill itemaMallSeckill = redisCache.getCacheObject(Constants.SECKILL_KEY + seckillId);
        if (itemaMallSeckill == null) {
            itemaMallSeckill = itemaMallSeckillMapper.selectByPrimaryKey(seckillId);
            redisCache.setCacheObject(Constants.SECKILL_KEY + seckillId, itemaMallSeckill, 24, TimeUnit.HOURS);
        }
        // 判断秒杀商品是否再有效期内
        long beginTime = itemaMallSeckill.getSeckillBegin().getTime();
        long endTime = itemaMallSeckill.getSeckillEnd().getTime();
        Date now = new Date();
        long nowTime = now.getTime();
        if (nowTime < beginTime) {
            throw new ItemaMallException("秒杀未开启");
        } else if (nowTime > endTime) {
            throw new ItemaMallException("秒杀已结束");
        }

        Date killTime = new Date();
        Map<String, Object> map = new HashMap<>(8);
        map.put("seckillId", seckillId);
        map.put("userId", userId);
        map.put("killTime", killTime);
        map.put("result", null);
        // 执行存储过程，result被赋值
        try {
            itemaMallSeckillMapper.killByProcedure(map);
        } catch (Exception e) {
            throw new ItemaMallException(e.getMessage());
        }
        // 获取result -2sql执行失败 -1未插入数据 0未更新数据 1sql执行成功
        map.get("result");
        int result = MapUtils.getInteger(map, "result", -2);
        if (result != 1) {
            throw new ItemaMallException("很遗憾！未抢购到秒杀商品");
        }
        // 记录购买过的用户
        redisCache.setCacheSet(Constants.SECKILL_SUCCESS_USER_ID + seckillId, userId);
        long endExpireTime = endTime / 1000;
        long nowExpireTime = nowTime / 1000;
        redisCache.expire(Constants.SECKILL_SUCCESS_USER_ID + seckillId, endExpireTime - nowExpireTime, TimeUnit.SECONDS);
        ItemaMallSeckillSuccess seckillSuccess = itemaMallSeckillSuccessMapper.getSeckillSuccessByUserIdAndSeckillId(userId, seckillId);
        SeckillSuccessVO seckillSuccessVO = new SeckillSuccessVO();
        Long seckillSuccessId = seckillSuccess.getSecId();
        seckillSuccessVO.setSeckillSuccessId(seckillSuccessId);
        seckillSuccessVO.setMd5(MD5Util.MD5Encode(seckillSuccessId + Constants.SECKILL_ORDER_SALT, Constants.UTF_ENCODING));
        return seckillSuccessVO;
    }

}
