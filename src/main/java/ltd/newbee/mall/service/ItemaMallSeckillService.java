package ltd.newbee.mall.service;

import ltd.newbee.mall.controller.vo.ExposerVO;
import ltd.newbee.mall.controller.vo.SeckillSuccessVO;
import ltd.newbee.mall.entity.ItemaMallSeckill;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

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
