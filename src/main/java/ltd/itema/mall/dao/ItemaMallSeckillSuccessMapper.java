package ltd.itema.mall.dao;

import ltd.itema.mall.entity.ItemaMallSeckillSuccess;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemaMallSeckillSuccessMapper {
    int deleteByPrimaryKey(Integer secId);

    int insert(ItemaMallSeckillSuccess record);

    int insertSelective(ItemaMallSeckillSuccess record);

    ItemaMallSeckillSuccess selectByPrimaryKey(Long secId);

    int updateByPrimaryKeySelective(ItemaMallSeckillSuccess record);

    int updateByPrimaryKey(ItemaMallSeckillSuccess record);

    ItemaMallSeckillSuccess getSeckillSuccessByUserIdAndSeckillId(Long userId, Long seckillId);
}
