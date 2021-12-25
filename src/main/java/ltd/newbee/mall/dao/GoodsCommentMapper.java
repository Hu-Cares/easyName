package ltd.newbee.mall.dao;

import io.lettuce.core.dynamic.annotation.Param;
import ltd.newbee.mall.entity.GoodsComment;

import java.util.List;

public interface GoodsCommentMapper {
    // 根据商品id查询该商品下的所有评论
    List<GoodsComment> findByGoodId(@Param("gId") Integer gId);
}