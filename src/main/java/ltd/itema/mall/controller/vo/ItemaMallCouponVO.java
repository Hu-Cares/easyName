package ltd.itema.mall.controller.vo;

import java.time.LocalDate;
import java.util.StringJoiner;

public class ItemaMallCouponVO {

    private Long couponId;

    private Long couponUserId;

    private String couponName;

    private String couponDesc;

    private Integer couponTotal;

    private boolean saleOut;

    private boolean isUsed;

    private Integer discount;

    private Integer min;

    private Byte couponLimit;

    private Byte couponType;

    private Byte status;

    private Byte goodsType;

    private String goodsValue;

    private String code;

    private LocalDate couponStartTime;

    private LocalDate couponEndTime;

    private boolean hasReceived;

    public Long getCouponUserId() {
        return couponUserId;
    }

    public void setCouponUserId(Long couponUserId) {
        this.couponUserId = couponUserId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public ItemaMallCouponVO setCouponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public ItemaMallCouponVO setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
        return this;
    }

    public Integer getCouponTotal() {
        return couponTotal;
    }

    public ItemaMallCouponVO setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
        return this;
    }

    public boolean isSaleOut() {
        return saleOut;
    }

    public ItemaMallCouponVO setSaleOut(boolean saleOut) {
        this.saleOut = saleOut;
        return this;
    }

    public Integer getDiscount() {
        return discount;
    }

    public ItemaMallCouponVO setDiscount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public Integer getMin() {
        return min;
    }

    public ItemaMallCouponVO setMin(Integer min) {
        this.min = min;
        return this;
    }

    public Byte getCouponLimit() {
        return couponLimit;
    }

    public ItemaMallCouponVO setCouponLimit(Byte couponLimit) {
        this.couponLimit = couponLimit;
        return this;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public ItemaMallCouponVO setCouponType(Byte couponType) {
        this.couponType = couponType;
        return this;
    }

    public Byte getStatus() {
        return status;
    }

    public ItemaMallCouponVO setStatus(Byte status) {
        this.status = status;
        return this;
    }

    public Byte getGoodsType() {
        return goodsType;
    }

    public ItemaMallCouponVO setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
        return this;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public ItemaMallCouponVO setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ItemaMallCouponVO setCode(String code) {
        this.code = code;
        return this;
    }

    public LocalDate getCouponStartTime() {
        return couponStartTime;
    }

    public ItemaMallCouponVO setCouponStartTime(LocalDate couponStartTime) {
        this.couponStartTime = couponStartTime;
        return this;
    }

    public LocalDate getCouponEndTime() {
        return couponEndTime;
    }

    public ItemaMallCouponVO setCouponEndTime(LocalDate couponEndTime) {
        this.couponEndTime = couponEndTime;
        return this;
    }

    public boolean isHasReceived() {
        return hasReceived;
    }

    public ItemaMallCouponVO setHasReceived(boolean hasReceived) {
        this.hasReceived = hasReceived;
        return this;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public ItemaMallCouponVO setUsed(boolean used) {
        isUsed = used;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ItemaMallCouponVO.class.getSimpleName() + "[", "]")
                .add("couponId=" + couponId)
                .add("couponUserId=" + couponUserId)
                .add("couponName='" + couponName + "'")
                .add("couponDesc='" + couponDesc + "'")
                .add("couponTotal=" + couponTotal)
                .add("saleOut=" + saleOut)
                .add("isUsed=" + isUsed)
                .add("discount=" + discount)
                .add("min=" + min)
                .add("couponLimit=" + couponLimit)
                .add("couponType=" + couponType)
                .add("status=" + status)
                .add("goodsType=" + goodsType)
                .add("goodsValue='" + goodsValue + "'")
                .add("code='" + code + "'")
                .add("couponStartTime=" + couponStartTime)
                .add("couponEndTime=" + couponEndTime)
                .add("hasReceived=" + hasReceived)
                .toString();
    }
}
