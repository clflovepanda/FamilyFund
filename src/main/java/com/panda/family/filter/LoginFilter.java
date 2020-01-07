package com.panda.family.filter;

import com.panda.family.domain.User;
import com.panda.family.service.UserService;
import com.panda.family.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            ThreadLocalUtil.addCookie(cookie.getName(), cookie);
        }
        Cookie cookie = ThreadLocalUtil.getCookie("userName");
        if (cookie == null) {//未登录状态
            response.sendRedirect("/login.html");
            return false;
        }
        User user = userService.queryUserByUserName(cookie.getValue());
        if (user == null) {
            response.sendRedirect("/login.html");
            return false;
        }
        ThreadLocalUtil.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
