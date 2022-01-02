package ltd.newbee.mall.interceptor;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.controller.vo.ItemaUserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ShopLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        if (null == request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        else if(((ItemaUserVO)request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY)).getIsMerchant()==0){
            response.sendRedirect(request.getContextPath() + "/shop_register");
            return false;
        }
        else return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
