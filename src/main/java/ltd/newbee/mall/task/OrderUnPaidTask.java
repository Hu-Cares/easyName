package ltd.newbee.mall.task;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ItemaMallOrderStatusEnum;
import ltd.newbee.mall.dao.ItemaGoodsMapper;
import ltd.newbee.mall.dao.ItemaMallOrderItemMapper;
import ltd.newbee.mall.dao.ItemaMallOrderMapper;
import ltd.newbee.mall.dao.ItemaMallSeckillMapper;
import ltd.newbee.mall.entity.ItemaMallOrder;
import ltd.newbee.mall.entity.ItemaMallOrderItem;
import ltd.newbee.mall.redis.RedisCache;
import ltd.newbee.mall.service.ItemaMallCouponService;
import ltd.newbee.mall.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * 未支付订单超时自动取消任务
 */
public class OrderUnPaidTask extends Task {
    /**
     * 默认延迟时间30分钟，单位毫秒
     */
    private static final long DELAY_TIME = 30 * 60 * 1000;

    private final Logger log = LoggerFactory.getLogger(OrderUnPaidTask.class);
    /**
     * 订单id
     */
    private final Long orderId;

    public OrderUnPaidTask(Long orderId, long delayInMilliseconds) {
        super("OrderUnPaidTask-" + orderId, delayInMilliseconds);
        this.orderId = orderId;
    }

    public OrderUnPaidTask(Long orderId) {
        super("OrderUnPaidTask-" + orderId, DELAY_TIME);
        this.orderId = orderId;
    }

    @Override
    public void run() {
        log.info("系统开始处理延时任务---订单超时未付款--- {}", this.orderId);

        ItemaMallOrderMapper itemaMallOrderMapper = SpringContextUtil.getBean(ItemaMallOrderMapper.class);
        ItemaMallOrderItemMapper itemaMallOrderItemMapper = SpringContextUtil.getBean(ItemaMallOrderItemMapper.class);
        ItemaGoodsMapper itemaGoodsMapper = SpringContextUtil.getBean(ItemaGoodsMapper.class);
        ItemaMallCouponService itemaMallCouponService = SpringContextUtil.getBean(ItemaMallCouponService.class);

        ItemaMallOrder order = itemaMallOrderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            log.info("系统结束处理延时任务---订单超时未付款--- {}", this.orderId);
            return;
        }
        if (order.getOrderStatus() != ItemaMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
            log.info("系统结束处理延时任务---订单超时未付款--- {}", this.orderId);
            return;
        }

        // 设置订单为已取消状态
        order.setOrderStatus((byte) ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus());
        order.setUpdateTime(new Date());
        if (itemaMallOrderMapper.updateByPrimaryKey(order) <= 0) {
            throw new RuntimeException("更新数据已失效");
        }

        // 商品货品数量增加
        List<ItemaMallOrderItem> itemaMallOrderItems = itemaMallOrderItemMapper.selectByOrderId(orderId);
        for (ItemaMallOrderItem orderItem : itemaMallOrderItems) {
            if (orderItem.getSeckillId() != null) {
                Long seckillId = orderItem.getSeckillId();
                ItemaMallSeckillMapper itemaMallSeckillMapper = SpringContextUtil.getBean(ItemaMallSeckillMapper.class);
                RedisCache redisCache = SpringContextUtil.getBean(RedisCache.class);
                if (!itemaMallSeckillMapper.addStock(seckillId)) {
                    throw new RuntimeException("秒杀商品货品库存增加失败");
                }
                redisCache.increment(Constants.SECKILL_GOODS_STOCK_KEY + seckillId);
            } else {
                Long goodsId = orderItem.getGoodsId();
                Integer goodsCount = orderItem.getGoodsCount();
                if (!itemaGoodsMapper.addStock(goodsId, goodsCount)) {
                    throw new RuntimeException("商品货品库存增加失败");
                }
            }
        }

        // 返还优惠券
        itemaMallCouponService.releaseCoupon(orderId);
        log.info("系统结束处理延时任务---订单超时未付款--- {}", this.orderId);
    }
}
