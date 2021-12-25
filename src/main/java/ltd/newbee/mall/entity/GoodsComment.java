package ltd.newbee.mall.entity;

import com.alipay.api.domain.Goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsComment {
    private Integer gcId;
    private Integer gcGid;
    private Integer gcUid;
    private Date gcTime;
    private String gcContent;
    private Integer gcStatus;
    private Integer gcParentid;
    private NewBeeMallGoods goods;
    private MallUser users;
    private List<GoodsComment> child;

    /*
    *gc_id：评论回复表id
    gc_uid：评论用户id（外键）
    gc_content：评论内容
    gc_time：评论时间
    gc_gid：评论的商品id（外键）
    gc_status：评论状态（1未删除0已删除）
    gc_parentid：父级评论的id，如果不是对评论的回复，那么该值为null
    */

    public Integer getGcParentid() {
        return gcParentid;
    }

    public List<GoodsComment> getChild() {
        return child;
    }

    public void setChild(List<GoodsComment> child) {
        this.child = child;
    }

    public Integer getGcId() {
        return gcId;
    }

    public void setGcContent(String gcContent) {
        this.gcContent = gcContent;
    }

    public void setGcGid(Integer gcGid) {
        this.gcGid = gcGid;
    }

    public void setGcId(Integer gcId) {
        this.gcId = gcId;
    }

    public void setGcParentid(Integer gcParentid) {
        this.gcParentid = gcParentid;
    }

    public void setGcStatus(Integer gcStatus) {
        this.gcStatus = gcStatus;
    }

    public MallUser getUsers() {
        return users;
    }

    public void setGcTime(Date gcTime) {
        this.gcTime = gcTime;
    }

    public void setGcUid(Integer gcUid) {
        this.gcUid = gcUid;
    }

    public void setGoods(NewBeeMallGoods goods) {
        this.goods = goods;
    }

    public void setUsers(MallUser users) {
        this.users = users;
    }

    public Date getGcTime() {
        return gcTime;
    }

    public Integer getGcGid() {
        return gcGid;
    }

    public Integer getGcStatus() {
        return gcStatus;
    }

    public Integer getGcUid() {
        return gcUid;
    }

    public NewBeeMallGoods getGoods() {
        return goods;
    }

    public String getGcContent() {
        return gcContent;
    }


}
