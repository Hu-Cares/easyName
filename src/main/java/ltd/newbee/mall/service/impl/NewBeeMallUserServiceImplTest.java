package ltd.newbee.mall.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.alipay.api.domain.Shop;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.controller.vo.NewBeeMallShopVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.dao.MallShopMapper;
import ltd.newbee.mall.entity.MallShop;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.service.NewBeeMallUserService;
import org.apache.ibatis.jdbc.Null;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RunWith(SpringRunner.class)
@SpringBootTest
class NewBeeMallUserServiceImplTest  {
    @Autowired
    private NewBeeMallUserService service;
    @Autowired
    private HttpServletRequest request;
    @Test
   void register(){
        System.out.println(service.shop_register("天猫商城","361121200205290512","吕俊","15707061101"));
    }
   @Test
    void login(){
       HttpSession session=request.getSession();
       service.login("15707061101","2a19cb7daff53d0166c5b4257dc3f62d",session);
       NewBeeMallUserVO newBeeMallShopVO=(NewBeeMallUserVO) session.getAttribute(Constants.MALL_USER_SESSION_KEY);
       System.out.println(newBeeMallShopVO.toString());//newBeeMallShopVO.toString()
   }
}
