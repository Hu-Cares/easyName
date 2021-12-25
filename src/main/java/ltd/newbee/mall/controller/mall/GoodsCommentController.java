package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.entity.GoodsComment;
import ltd.newbee.mall.service.GoodsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/goodsComment")
public class GoodsCommentController {

    @Autowired
    private GoodsCommentService commentService;

    /**
     * 根据商品id查询该商品下的所有评论
     * @param gId
     * @return
     */
    @GetMapping("/findByGoodId/{gId}")
    public List<GoodsComment> findByGoodId(@PathVariable("gId") Integer gId) {
        List<GoodsComment> allComments = commentService.findByGoodId(gId);
        if (allComments == null || allComments.size() == 0) {
            return new ArrayList<>();
        }
        List<GoodsComment> comments = new ArrayList<>();
        List<GoodsComment> parents = new ArrayList<>();
        for (GoodsComment comment : allComments) {
            if (comment.getGcParentid()==null) {
                comments.add(comment);
                parents.add(comment);
            } else {
                boolean foundParent=false;
                for (GoodsComment parent : parents) {
                    if (comment.getGcParentid() == parent.getGcId()) {
                        if (parent.getChild() == null) {
                            parent.setChild(new ArrayList<>());
                        }
                        parent.getChild().add(comment);
                        parents.add(comment);
                        foundParent=true;
                        //如果对list迭代过程中同时修改list，会报java.util.ConcurrentModificationException
                        // 的异常，所以我们需要break,当然break也可以提高算法效率
                        break;
                    }
                }
                if (!foundParent) {
                    throw new RuntimeException("can not find the parent comment");
                }
            }
        }
        return comments;
    }
}
