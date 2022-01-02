package ltd.newbee.mall.controller.vo;

import java.io.Serializable;
import java.util.StringJoiner;

public class ItemaMallShopVO {
    private Long shopId;
    private String shopName;
    private String realName;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "NewBeeMallShopVO{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}

