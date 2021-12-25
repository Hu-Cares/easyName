package ltd.newbee.mall.service.impl;

import ltd.newbee.mall.dao.GoodsCommentMapper;
import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.service.GoodsCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsCommentServiceImpl implements GoodsCommentService {
    @Resource
    private GoodsCommentMapper goodsCommentMapper;

    @Override
    public List<GoodsComment> findByGoodId(Integer gId) {
        return goodsCommentMapper.findByGoodId(gId);
    }
}
