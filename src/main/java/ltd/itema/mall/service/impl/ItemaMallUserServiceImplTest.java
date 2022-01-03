package ltd.itema.mall.service.impl;

import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.service.ItemaMallUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RunWith(SpringRunner.class)
@SpringBootTest
class ItemaMallUserServiceImplTest {
    @Autowired
    private ItemaMallUserService service;
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
       ItemaMallUserVO newBeeMallShopVO=(ItemaMallUserVO) session.getAttribute(Constants.MALL_SHOP_SESSION_KEY);
       System.out.println(newBeeMallShopVO.toString());//newBeeMallShopVO.toString()
   }
}
