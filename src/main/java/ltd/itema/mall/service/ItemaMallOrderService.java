/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.service;

import ltd.itema.mall.controller.vo.ItemaMallOrderDetailVO;
import ltd.itema.mall.controller.vo.ItemaMallOrderItemVO;
import ltd.itema.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.entity.ItemaMallOrder;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;

import java.util.List;

public interface ItemaMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getItemaMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param itemaMallOrder
     * @return
     */
    String updateOrderInfo(ItemaMallOrder itemaMallOrder);

    /**
     * 根据主键修改订单信息
     *
     * @param itemaMallOrder
     * @return
     */
    boolean updateByPrimaryKeySelective(ItemaMallOrder itemaMallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     *
     * @param user
     * @param couponUserId
     * @param myShoppingCartItems
     * @return
     */
    String saveOrder(ItemaMallUserVO user, Long couponUserId, List<ItemaMallShoppingCartItemVO> myShoppingCartItems);

    /**
     * 生成秒杀订单
     *
     * @param seckillSuccessId
     * @param userId
     * @return
     */
    String seckillSaveOrder(Long seckillSuccessId, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    ItemaMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    ItemaMallOrder getItemaMallOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    List<ItemaMallOrderItemVO> getOrderItems(Long id);
}
