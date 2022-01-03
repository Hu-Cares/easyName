/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.itema.mall.controller.mall;

import ltd.itema.mall.controller.vo.ItemaMallUserVO;
import ltd.itema.mall.common.Constants;
import ltd.itema.mall.common.ItemaMallException;
import ltd.itema.mall.common.ServiceResultEnum;
import ltd.itema.mall.entity.MallUser;
import ltd.itema.mall.service.ItemaMallCouponService;
import ltd.itema.mall.service.ItemaMallUserService;
import ltd.itema.mall.util.HttpUtil;
import ltd.itema.mall.util.MD5Util;
import ltd.itema.mall.util.Result;
import ltd.itema.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PersonalController {

    @Resource
    private ItemaMallUserService itemaMallUserService;

    @Autowired
    private ItemaMallCouponService itemaMallCouponService;

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage(HttpServletRequest request) {
        if (HttpUtil.isAjaxRequest(request)) {
            throw new ItemaMallException("请先登陆！");
        }
        return "mall/login";
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

    @GetMapping("/shop_register")
    public String shop_registerPage(){return "shop/register";}  //商家注册

    @GetMapping("/shop_login")
    public String shop_loginPage(){return "shop/login";}  //商家登录

    @GetMapping({"/shop", "/shop/", "/shop/index", "/shop/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "shop/index";
    }
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        httpSession.setAttribute(Constants.MALL_VERIFY_CODE_KEY, null);
        String loginResult = itemaMallUserService.login(loginName, MD5Util.MD5Encode(password, Constants.UTF_ENCODING), httpSession);
        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        httpSession.setAttribute(Constants.MALL_VERIFY_CODE_KEY, null);
        String registerResult = itemaMallUserService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

//商家注册
    @PostMapping("/shop_register")
    @ResponseBody
    public Result shop_register(@RequestParam("shopName") String shopName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("idCard") String idCard,
                           @RequestParam("realName") String realName,
                           @RequestParam("loginName") String loginName,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(shopName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum. SAME_SHOP_NAME_EXIST.getResult());
        }
        if (StringUtils.isEmpty(idCard)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_ID_CARD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        if (StringUtils.isEmpty(realName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_REAL_NAME_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.toLowerCase().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        httpSession.setAttribute(Constants.MALL_VERIFY_CODE_KEY, null);
        String registerResult = itemaMallUserService.shop_register(shopName, idCard,realName,loginName);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            //删除session中的verifyCode
            httpSession.removeAttribute(Constants.MALL_VERIFY_CODE_KEY);
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        ItemaMallUserVO mallUserTemp = itemaMallUserService.updateUserInfo(mallUser, httpSession);
        if (mallUserTemp == null) {
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        } else {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }
}
