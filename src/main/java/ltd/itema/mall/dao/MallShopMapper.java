package ltd.itema.mall.dao;
import ltd.itema.mall.entity.MallShop;
import org.springframework.stereotype.Repository;

@Repository
public interface MallShopMapper {

    int insert(MallShop record);

    int insertSelective(MallShop record);

    MallShop selectByLoginName(String loginName);

    MallShop selectByShopName(String loginName);

    MallShop selectByPrimaryKey(Long shopId);

}