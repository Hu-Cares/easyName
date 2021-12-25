package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.GoodsComment;

import java.util.List;

public interface GoodsCommentService {
    // 根据商品id查询该商品下的所有评论
    List<GoodsComment> findByGoodId(Integer gId);
}