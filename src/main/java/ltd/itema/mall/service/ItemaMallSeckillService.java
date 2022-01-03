package ltd.itema.mall.service;

import ltd.itema.mall.controller.vo.ExposerVO;
import ltd.itema.mall.controller.vo.SeckillSuccessVO;
import ltd.itema.mall.entity.ItemaMallSeckill;
import ltd.itema.mall.util.PageQueryUtil;
import ltd.itema.mall.util.PageResult;

import java.util.List;

public interface ItemaMallSeckillService {

    PageResult getSeckillPage(PageQueryUtil pageUtil);

    boolean saveSeckill(ItemaMallSeckill itemaMallSeckill);

    boolean updateSeckill(ItemaMallSeckill itemaMallSeckill);

    ItemaMallSeckill getSeckillById(Long id);

    boolean deleteSeckillById(Long id);

    List<ItemaMallSeckill> getHomeSeckillPage();

    ExposerVO exposerUrl(Long seckillId);

    SeckillSuccessVO executeSeckill(Long seckillId, Long userId);
}
