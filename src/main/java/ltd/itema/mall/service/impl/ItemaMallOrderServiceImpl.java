/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service.impl;

import ltd.itema.mall.common.*;
import ltd.itema.mall.controller.vo.*;
import ltd.itema.mall.dao.*;
import ltd.itema.mall.entity.*;
import ltd.itema.mall.common.*;
import ltd.itema.mall.config.ProjectConfig;
import ltd.itema.mall.controller.vo.*;
import ltd.itema.mall.dao.*;
import ltd.itema.mall.entity.*;
import ltd.itema.mall.service.ItemaMallOrderService;
import ltd.itema.mall.task.OrderUnPaidTask;
import ltd.itema.mall.task.TaskService;
import ltd.itema.mall.util.BeanUtil;
import ltd.itema.mall.util.NumberUtil;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ItemaMallOrderServiceImpl implements ItemaMallOrderService {

    @Autowired
    private ItemaMallOrderMapper itemaMallOrderMapper;
    @Autowired
    private ItemaMallOrderItemMapper itemaMallOrderItemMapper;
    @Autowired
    private ItemaMallShoppingCartItemMapper itemaMallShoppingCartItemMapper;
    @Autowired
    private ItemaMallGoodsMapper itemaMallGoodsMapper;
    @Autowired
    private ItemaMallUserCouponRecordMapper itemaMallUserCouponRecordMapper;
    @Autowired
    private ItemaMallCouponMapper itemaMallCouponMapper;
    @Autowired
    private ItemaMallSeckillMapper itemaMallSeckillMapper;
    @Autowired
    private ItemaMallSeckillSuccessMapper itemaMallSeckillSuccessMapper;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil) {
        List<ItemaMallOrder> itemaMallOrders = itemaMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        MallShop mallshop=(MallShop)request.getSession().getAttribute(Constants.MALL_SHOP_SESSION_KEY);
         if (mallshop!=null&&null == request.getSession().getAttribute("loginUser")) {
             Iterator<ItemaMallOrder> it = itemaMallOrders.iterator();
             while (it.hasNext()) {
                 ItemaMallOrder x = it.next();
                 if ((x.getShopId()) != (mallshop.getShopId())) {
                     it.remove();
                 }
             }
         }
        int total = itemaMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        PageResult pageResult = new PageResult(itemaMallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(ItemaMallOrder itemaMallOrder) {
        ItemaMallOrder temp = itemaMallOrderMapper.selectByPrimaryKey(itemaMallOrder.getOrderId());
        // 不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(itemaMallOrder.getTotalPrice());
            temp.setUserAddress(itemaMallOrder.getUserAddress());
            temp.setUpdateTime(new Date());
            if (itemaMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public boolean updateByPrimaryKeySelective(ItemaMallOrder itemaMallOrder) {
        return itemaMallOrderMapper.updateByPrimaryKeySelective(itemaMallOrder) > 0;
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        // 查询所有的订单 判断状态 修改状态和更新时间
        List<ItemaMallOrder> orders = itemaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));

        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (ItemaMallOrder itemaMallOrder : orders) {
                if (itemaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (itemaMallOrder.getOrderStatus() != 1) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                // 订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (itemaMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        // 未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        // 查询所有的订单 判断状态 修改状态和更新时间
        List<ItemaMallOrder> orders = itemaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (ItemaMallOrder itemaMallOrder : orders) {
                if (itemaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (itemaMallOrder.getOrderStatus() != 1 && itemaMallOrder.getOrderStatus() != 2) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                // 订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (itemaMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        // 未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        // 查询所有的订单 判断状态 修改状态和更新时间
        List<ItemaMallOrder> orders = itemaMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (ItemaMallOrder itemaMallOrder : orders) {
                // isDeleted=1 一定为已关闭订单
                if (itemaMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                    continue;
                }
                // 已关闭或者已完成无法关闭订单
                if (itemaMallOrder.getOrderStatus() == 4 || itemaMallOrder.getOrderStatus() < 0) {
                    errorOrderNos += itemaMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                // 订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (itemaMallOrderMapper.closeOrder(Arrays.asList(ids), ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                // 订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        // 未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrder(ItemaMallUserVO user, Long couponUserId, List<ItemaMallShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(ItemaMallShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(ItemaMallShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<ItemaMallGoods> itemaMallGoods = itemaMallGoodsMapper.selectByPrimaryKeys(goodsIds);
        // 检查是否包含已下架商品
        List<ItemaMallGoods> goodsListNotSelling = itemaMallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            // goodsListNotSelling 对象非空则表示有下架商品
            ItemaMallException.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，无法生成订单");
        }
        Map<Long, ItemaMallGoods> newBeeMallGoodsMap = itemaMallGoods.stream().collect(Collectors.toMap(ItemaMallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        // 判断商品库存
        int i=0;
        for (ItemaMallShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            // 查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                ItemaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            // 存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                ItemaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        if (CollectionUtils.isEmpty(itemIdList) || CollectionUtils.isEmpty(goodsIds) || CollectionUtils.isEmpty(itemaMallGoods)) {
            ItemaMallException.fail(ServiceResultEnum.ORDER_GENERATE_ERROR.getResult());
        }
        if (itemaMallShoppingCartItemMapper.deleteBatch(itemIdList) <= 0) {
            ItemaMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
        int updateStockNumResult = itemaMallGoodsMapper.updateStockNum(stockNumDTOS);
        if (updateStockNumResult < 1) {
            ItemaMallException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
        }
        // 生成订单号
        Integer totalpriceTotal=0;
        int l=0;
        ArrayList<String> oderNoo =  new ArrayList<String>();
        for(ItemaMallGoods o: itemaMallGoods) {
            String orderNo = NumberUtil.genOrderNo();
            int priceTotal = 0;
            totalpriceTotal=0;
            // 保存订单
            ItemaMallOrder itemaMallOrder = new ItemaMallOrder();
            itemaMallOrder.setOrderNo(orderNo);
            itemaMallOrder.setUserId(user.getUserId());
            itemaMallOrder.setUserAddress(user.getAddress());
            itemaMallOrder.setShopId(o.getShopId());
            itemaMallOrder.setGoodsId(o.getGoodsId());

            for (ItemaMallShoppingCartItemVO itemaMallShoppingCartItemVO : myShoppingCartItems) {
                totalpriceTotal += itemaMallShoppingCartItemVO.getGoodsCount() * itemaMallShoppingCartItemVO.getSellingPrice();
            }
            priceTotal += myShoppingCartItems.get(i).getGoodsCount() * myShoppingCartItems.get(i++).getSellingPrice();
            // 如果使用了优惠卷
            if (couponUserId != null) {
                ItemaMallUserCouponRecord itemaMallUserCouponRecord = itemaMallUserCouponRecordMapper.selectByPrimaryKey(couponUserId);
                ItemaMallCoupon itemaMallCoupon = itemaMallCouponMapper.selectByPrimaryKey(itemaMallUserCouponRecord.getCouponId());
                priceTotal -= itemaMallCoupon.getDiscount();
            }
            if (priceTotal < 1) {
                ItemaMallException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
            }
            itemaMallOrder.setTotalPrice(priceTotal);
            String extraInfo = "newbeemall-plus支付宝沙箱支付";
            itemaMallOrder.setExtraInfo(extraInfo);
            // 生成订单项并保存订单项纪录
            if (itemaMallOrderMapper.insertSelective(itemaMallOrder) <= 0) {
                ItemaMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            // 如果使用了优惠卷，则更新优惠卷状态
            if (couponUserId != null) {
                ItemaMallUserCouponRecord couponUser = new ItemaMallUserCouponRecord();
                couponUser.setCouponUserId(couponUserId);
                couponUser.setOrderId(itemaMallOrder.getOrderId());
                couponUser.setUseStatus((byte) 1);
                couponUser.setUsedTime(new Date());
                couponUser.setUpdateTime(new Date());
                itemaMallUserCouponRecordMapper.updateByPrimaryKeySelective(couponUser);
            }
            // 生成所有的订单项快照，并保存至数据库
            //List<NewBeeMallOrderItem> newBeeMallOrderItems = new ArrayList<>();
            //for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : myShoppingCartItems) {
                ItemaMallShoppingCartItemVO itemaMallShoppingCartItemVO =myShoppingCartItems.get(l++);
                ItemaMallOrderItem itemaMallOrderItem = new ItemaMallOrderItem();
                // 使用BeanUtil工具类将newBeeMallShoppingCartItemVO中的属性复制到newBeeMallOrderItem对象中
                BeanUtil.copyProperties(itemaMallShoppingCartItemVO, itemaMallOrderItem);
                // NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以获取到
                itemaMallOrderItem.setOrderId(itemaMallOrder.getOrderId());
             //   newBeeMallOrderItems.add(newBeeMallOrderItem);
           // }
            // 保存至数据库
            if (itemaMallOrderItemMapper.insert(itemaMallOrderItem) <= 0) {
                ItemaMallException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            // 订单支付超期任务，超过300秒自动取消订单
            taskService.addTask(new OrderUnPaidTask(itemaMallOrder.getOrderId(), ProjectConfig.getOrderUnpaidOverTime() * 1000));
            oderNoo.add(orderNo);
        }
        request.getSession().setAttribute("totalprice",totalpriceTotal);
         request.getSession().setAttribute("oderNoo",oderNoo);      
        // 所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
        return oderNoo.get(oderNoo.size()-1);
    }

    @Override
    public String seckillSaveOrder(Long seckillSuccessId, Long userId) {
        ItemaMallSeckillSuccess itemaMallSeckillSuccess = itemaMallSeckillSuccessMapper.selectByPrimaryKey(seckillSuccessId);
        if (!itemaMallSeckillSuccess.getUserId().equals(userId)) {
            throw new ItemaMallException("当前登陆用户与抢购秒杀商品的用户不匹配");
        }
        Long seckillId = itemaMallSeckillSuccess.getSeckillId();
        ItemaMallSeckill itemaMallSeckill = itemaMallSeckillMapper.selectByPrimaryKey(seckillId);
        Long goodsId = itemaMallSeckill.getGoodsId();
        ItemaMallGoods itemaMallGoods = itemaMallGoodsMapper.selectByPrimaryKey(goodsId);
        // 生成订单号
        String orderNo = NumberUtil.genOrderNo();
        // 保存订单
        ItemaMallOrder itemaMallOrder = new ItemaMallOrder();
        itemaMallOrder.setOrderNo(orderNo);
        itemaMallOrder.setTotalPrice(itemaMallSeckill.getSeckillPrice());
        itemaMallOrder.setUserId(userId);
        itemaMallOrder.setUserAddress("秒杀测试地址");
        itemaMallOrder.setOrderStatus((byte) ItemaMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
        itemaMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
        itemaMallOrder.setPayType((byte) PayTypeEnum.WEIXIN_PAY.getPayType());
        itemaMallOrder.setPayTime(new Date());
        String extraInfo = "";
        itemaMallOrder.setExtraInfo(extraInfo);
        if (itemaMallOrderMapper.insertSelective(itemaMallOrder) <= 0) {
            throw new ItemaMallException("生成订单内部异常");
        }
        // 保存订单商品项
        ItemaMallOrderItem itemaMallOrderItem = new ItemaMallOrderItem();
        Long orderId = itemaMallOrder.getOrderId();
        itemaMallOrderItem.setOrderId(orderId);
        itemaMallOrderItem.setSeckillId(seckillId);
        itemaMallOrderItem.setGoodsId(itemaMallGoods.getGoodsId());
        itemaMallOrderItem.setGoodsCoverImg(itemaMallGoods.getGoodsCoverImg());
        itemaMallOrderItem.setGoodsName(itemaMallGoods.getGoodsName());
        itemaMallOrderItem.setGoodsCount(1);
        itemaMallOrderItem.setSellingPrice(itemaMallSeckill.getSeckillPrice());
        if (itemaMallOrderItemMapper.insert(itemaMallOrderItem) <= 0) {
            throw new ItemaMallException("生成订单内部异常");
        }
        // 订单支付超期任务
        taskService.addTask(new OrderUnPaidTask(itemaMallOrder.getOrderId(), 30 * 1000));
        return orderNo;
    }

    @Override
    public ItemaMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByOrderNo(orderNo);
        if (itemaMallOrder == null) {
            return null;
        }
        
        // 验证是否是当前userId下的订单，否则报错
        if (!userId.equals(itemaMallOrder.getUserId())) {
            ItemaMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
        }
        itemaMallOrder.setTotalPrice((Integer)request.getSession().getAttribute("totalprice"));
        List<ItemaMallOrderItem> orderItems = itemaMallOrderItemMapper.selectByOrderId(itemaMallOrder.getOrderId());
        // 获取订单项数据
        if (CollectionUtils.isEmpty(orderItems)) {
            return null;
        }
        List<ItemaMallOrderItemVO> itemaMallOrderItemVOS = BeanUtil.copyList(orderItems, ItemaMallOrderItemVO.class);
        ItemaMallOrderDetailVO itemaMallOrderDetailVO = new ItemaMallOrderDetailVO();
        BeanUtil.copyProperties(itemaMallOrder, itemaMallOrderDetailVO);
        itemaMallOrderDetailVO.setOrderStatusString(ItemaMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(itemaMallOrderDetailVO.getOrderStatus()).getName());
        itemaMallOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(itemaMallOrderDetailVO.getPayType()).getName());
        itemaMallOrderDetailVO.setItemaMallOrderItemVOS(itemaMallOrderItemVOS);
        ItemaMallUserCouponRecord itemaMallUserCouponRecord = itemaMallUserCouponRecordMapper.getUserCouponByOrderId(itemaMallOrder.getOrderId());
        if (itemaMallUserCouponRecord != null) {
            ItemaMallCoupon itemaMallCoupon = itemaMallCouponMapper.selectByPrimaryKey(itemaMallUserCouponRecord.getCouponId());
            itemaMallOrderDetailVO.setDiscount(itemaMallCoupon.getDiscount());
        }
        return itemaMallOrderDetailVO;
    }

    @Override
    public ItemaMallOrder getNewBeeMallOrderByOrderNo(String orderNo) {
        ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByOrderNo(orderNo);
        return itemaMallOrder;
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = itemaMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        List<ItemaMallOrder> itemaMallOrders = itemaMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        List<ItemaMallOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            // 数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(itemaMallOrders, ItemaMallOrderListVO.class);
            // 设置订单状态中文显示值
            for (ItemaMallOrderListVO itemaMallOrderListVO : orderListVOS) {
                itemaMallOrderListVO.setOrderStatusString(ItemaMallOrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(itemaMallOrderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = itemaMallOrders.stream().map(ItemaMallOrder::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                List<ItemaMallOrderItem> orderItems = itemaMallOrderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<ItemaMallOrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(ItemaMallOrderItem::getOrderId));
                for (ItemaMallOrderListVO itemaMallOrderListVO : orderListVOS) {
                    // 封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(itemaMallOrderListVO.getOrderId())) {
                        List<ItemaMallOrderItem> orderItemListTemp = itemByOrderIdMap.get(itemaMallOrderListVO.getOrderId());
                        // 将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<ItemaMallOrderItemVO> itemaMallOrderItemVOS = BeanUtil.copyList(orderItemListTemp, ItemaMallOrderItemVO.class);
                        itemaMallOrderListVO.setItemaMallOrderItemVOS(itemaMallOrderItemVOS);
                    }
                }
            }
        }
//        for (ItemaMallOrderListVO o : orderListVOS){
//            List<ItemaMallOrderItemVO> itemaMallOrderItemVO=o.getNewBeeMallOrderItemVOS();
//            for(ItemaMallOrderItemVO oo:itemaMallOrderItemVO)
//                System.out.println(oo.toString());
//        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByOrderNo(orderNo);
        if (itemaMallOrder != null) {
            // 验证是否是当前userId下的订单，否则报错
            if (!userId.equals(itemaMallOrder.getUserId())) {
                ItemaMallException.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            // 订单状态判断
            if (itemaMallOrder.getOrderStatus().intValue() == ItemaMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || itemaMallOrder.getOrderStatus().intValue() == ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || itemaMallOrder.getOrderStatus().intValue() == ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || itemaMallOrder.getOrderStatus().intValue() == ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            if (itemaMallOrderMapper.closeOrder(Collections.singletonList(itemaMallOrder.getOrderId()), ItemaMallOrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByOrderNo(orderNo);
        if (itemaMallOrder != null) {
            // 验证是否是当前userId下的订单，否则报错
            if (!userId.equals(itemaMallOrder.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            // 订单状态判断 非出库状态下不进行修改操作
            if (itemaMallOrder.getOrderStatus().intValue() != ItemaMallOrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            itemaMallOrder.setOrderStatus((byte) ItemaMallOrderStatusEnum.ORDER_SUCCESS.getOrderStatus());
            itemaMallOrder.setUpdateTime(new Date());
            if (itemaMallOrderMapper.updateByPrimaryKeySelective(itemaMallOrder) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        ArrayList<String> oderNoo=(ArrayList<String>)request.getSession().getAttribute("oderNoo");
        for(String oderNo1:oderNoo) {
            ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByOrderNo(oderNo1);
            if (itemaMallOrder == null) {
                return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
            }
            itemaMallOrder.setOrderStatus((byte) 0);
            if (itemaMallOrder.getOrderStatus().intValue() != ItemaMallOrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            itemaMallOrder.setOrderStatus((byte) ItemaMallOrderStatusEnum.ORDER_PAID.getOrderStatus());
            itemaMallOrder.setPayType((byte) payType);
            itemaMallOrder.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            itemaMallOrder.setPayTime(new Date());
            itemaMallOrder.setUpdateTime(new Date());
            if (itemaMallOrderMapper.updateByPrimaryKeySelective(itemaMallOrder) <= 0) {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
            taskService.removeTask(new OrderUnPaidTask(itemaMallOrder.getOrderId()));
        }
        return ServiceResultEnum.SUCCESS.getResult();
    }

    @Override
    public List<ItemaMallOrderItemVO> getOrderItems(Long id) {
        ItemaMallOrder itemaMallOrder = itemaMallOrderMapper.selectByPrimaryKey(id);
        if (itemaMallOrder != null) {
            List<ItemaMallOrderItem> orderItems = itemaMallOrderItemMapper.selectByOrderId(itemaMallOrder.getOrderId());
            // 获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<ItemaMallOrderItemVO> itemaMallOrderItemVOS = BeanUtil.copyList(orderItems, ItemaMallOrderItemVO.class);
                return itemaMallOrderItemVOS;
            }
        }
        return null;
    }
}
