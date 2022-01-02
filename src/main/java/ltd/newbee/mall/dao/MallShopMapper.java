package ltd.newbee.mall.dao;
import ltd.newbee.mall.entity.Shop;
import org.springframework.stereotype.Repository;

@Repository
public interface MallShopMapper {

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByLoginName(String loginName);

    Shop selectByShopName(String loginName);

    Shop selectByPrimaryKey(Long shopId);

}