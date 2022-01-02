package ltd.newbee.mall.entity;

import java.time.LocalDate;
import java.util.Date;

public class ItemaMallCoupon {
    private Long couponId;

    private String couponName;

    private String couponDesc;

    private Integer couponTotal;

    private Integer discount;

    private Integer min;

    private Byte couponLimit;

    private Byte couponType;

    private Byte couponStatus;

    private Byte goodsType;

    private String goodsValue;

    private String couponCode;

    private LocalDate couponStartTime;

    private LocalDate couponEndTime;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    public Long getCouponId() {
        return couponId;
    }

    public ItemaMallCoupon setCouponId(Long couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public ItemaMallCoupon setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public ItemaMallCoupon setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
        return this;
    }

    public Integer getCouponTotal() {
        return couponTotal;
    }

    public ItemaMallCoupon setCouponTotal(Integer couponTotal) {
        this.couponTotal = couponTotal;
        return this;
    }

    public Integer getDiscount() {
        return discount;
    }

    public ItemaMallCoupon setDiscount(Integer discount) {
        this.discount = discount;
        return this;
    }

    public Integer getMin() {
        return min;
    }

    public ItemaMallCoupon setMin(Integer min) {
        this.min = min;
        return this;
    }

    public Byte getCouponLimit() {
        return couponLimit;
    }

    public ItemaMallCoupon setCouponLimit(Byte couponLimit) {
        this.couponLimit = couponLimit;
        return this;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public ItemaMallCoupon setCouponType(Byte couponType) {
        this.couponType = couponType;
        return this;
    }

    public Byte getCouponStatus() {
        return couponStatus;
    }

    public ItemaMallCoupon setCouponStatus(Byte couponStatus) {
        this.couponStatus = couponStatus;
        return this;
    }

    public Byte getGoodsType() {
        return goodsType;
    }

    public ItemaMallCoupon setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
        return this;
    }

    public String getGoodsValue() {
        return goodsValue;
    }

    public ItemaMallCoupon setGoodsValue(String goodsValue) {
        this.goodsValue = goodsValue;
        return this;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public ItemaMallCoupon setCouponCode(String couponCode) {
        this.couponCode = couponCode;
        return this;
    }

    public LocalDate getCouponStartTime() {
        return couponStartTime;
    }

    public ItemaMallCoupon setCouponStartTime(LocalDate couponStartTime) {
        this.couponStartTime = couponStartTime;
        return this;
    }

    public LocalDate getCouponEndTime() {
        return couponEndTime;
    }

    public ItemaMallCoupon setCouponEndTime(LocalDate couponEndTime) {
        this.couponEndTime = couponEndTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ItemaMallCoupon setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public ItemaMallCoupon setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public ItemaMallCoupon setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }
}
