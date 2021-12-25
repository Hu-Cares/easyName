package ltd.newbee.mall.dao;
import ltd.newbee.mall.entity.MallShop;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MallShopMapper {

    int insert(MallShop record);

    int insertSelective(MallShop record);

    MallShop selectByLoginName(String loginName);

    MallShop selectByShopName(String loginName);

    MallShop selectByPrimaryKey(Long shopId);

}