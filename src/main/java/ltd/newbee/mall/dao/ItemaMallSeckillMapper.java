package ltd.newbee.mall.dao;

import ltd.newbee.mall.entity.ItemaMallSeckill;
import ltd.newbee.mall.util.PageQueryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ItemaMallSeckillMapper {
    int deleteByPrimaryKey(Long seckillId);

    int insert(ItemaMallSeckill record);

    int insertSelective(ItemaMallSeckill record);

    ItemaMallSeckill selectByPrimaryKey(Long seckillId);

    int updateByPrimaryKeySelective(ItemaMallSeckill record);

    int updateByPrimaryKey(ItemaMallSeckill record);

    List<ItemaMallSeckill> findSeckillList(PageQueryUtil pageUtil);

    int getTotalSeckills(PageQueryUtil pageUtil);

    List<ItemaMallSeckill> findHomeSeckillList();

    int getHomeTotalSeckills(PageQueryUtil pageUtil);

    void killByProcedure(Map<String, Object> map);

    boolean addStock(Long seckillId);
}
