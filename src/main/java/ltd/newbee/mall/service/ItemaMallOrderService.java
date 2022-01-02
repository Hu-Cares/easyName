 
package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.ItemaMallOrderDetailVO;
import ltd.newbee.mall.controller.vo.ItemaMallOrderItemVO;
import ltd.newbee.mall.controller.vo.ItemaMallShoppingCartItemVO;
import ltd.newbee.mall.controller.vo.ItemaUserVO;
import ltd.newbee.mall.entity.ItemaMallOrder;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

public interface ItemaMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil);

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
    String saveOrder(ItemaUserVO user, Long couponUserId, List<ItemaMallShoppingCartItemVO> myShoppingCartItems);

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
    ItemaMallOrder getNewBeeMallOrderByOrderNo(String orderNo);

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
