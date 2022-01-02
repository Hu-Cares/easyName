 
package ltd.newbee.mall.controller.vo;

import java.io.Serializable;
import java.util.StringJoiner;

public class ItemaUserVO implements Serializable {

    private Long userId;

    private String nickName;

    private String loginName;

    private String introduceSign;

    private String address;

    private Integer shopCartItemCount;

    private Byte isMerchant;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIntroduceSign() {
        return introduceSign;
    }

    public void setIntroduceSign(String introduceSign) {
        this.introduceSign = introduceSign;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getShopCartItemCount() {
        return shopCartItemCount;
    }

    public void setShopCartItemCount(Integer shopCartItemCount) {
        this.shopCartItemCount = shopCartItemCount;
    }

    public Byte getIsMerchant() {
        return isMerchant;
    }

    public void setIsMerchant(Byte isMerchant) {
        this.isMerchant = isMerchant;
    }


    @Override
    public String toString() {
        return "NewBeeMallUserVO{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", isMerchant='" + isMerchant + '\'' +
                ", introduceSign='" + introduceSign + '\'' +
                ", address='" + address + '\'' +
                ", shopCartItemCount=" + shopCartItemCount +
                '}';
    }
}
