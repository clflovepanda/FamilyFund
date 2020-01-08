package com.panda.family.controller;

import com.alibaba.fastjson.JSON;
import com.panda.family.domain.User;
import com.panda.family.service.UserService;
import com.panda.family.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PassportController extends BaseController{

    @Autowired
    UserService userService;

    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public void login(@RequestBody User user,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        if (!RegExpUtil.isUserName(user.getUserName())) {
            writeJson(response, ResultStatusEnum.FAILED, "用户名或密码不正确", null);
            return;
        }
        if (!RegExpUtil.isPassword(user.getPassword())) {
            writeJson(response, ResultStatusEnum.FAILED, "用户名或密码不正确", null);
            return;
        }
        boolean result = userService.checkUserPassword(user.getUserName(), user.getPassword());
        if (result) {
            Cookie cookie = new Cookie("userName", user.getUserName());
            cookie.setPath("/");
            cookie.setMaxAge(86400 * 7);
            response.addCookie(cookie);
            writeJson(response, ResultStatusEnum.SUCCESS, "登录成功", null);
            return;
        } else {
            writeJson(response, ResultStatusEnum.FAILED, "用户名或密码不正确", null);
            return;
        }
    }

    @RequestMapping(path = "/doRegister", method = RequestMethod.POST)
    public void register(@RequestBody User user,
                         HttpServletRequest reqruest,
                         HttpServletResponse response) throws IOException {

        user.setStatus(0);
        user.setCtime(TimeUtil.getNow());
        user.setUtime(TimeUtil.getNow());
        user.setIncome(0);
        user.setRealIncome(0);
        user.setCode(0);
        user.setCodeTime(0);

        if (!RegExpUtil.isUserName(user.getUserName())) {
            writeJson(response, ResultStatusEnum.FAILED, "用户名长度为4-18位，字母、数字组成", null);
            return;
        }
        if (!RegExpUtil.isNickname(user.getNickname())) {
            writeJson(response, ResultStatusEnum.FAILED, "昵称长度1-18位，汉字、字母、数字组成", null);
            return;
        }
        if (!RegExpUtil.isPassword(user.getPassword())) {
            writeJson(response, ResultStatusEnum.FAILED, "密码长度为6-18位，字母、数字组成", null);
            return;
        }
        if (!EmailUtil.checkEmailFormat(user.getEmail())) {
            writeJson(response, ResultStatusEnum.FAILED, "无法识别该邮箱，请更换一个邮箱", null);
            return;
        }

        User temp = userService.queryUserByUserName(user.getUserName());
        if (temp != null) {
            writeJson(response, ResultStatusEnum.FAILED, "用户名重复", null);
            return;
        }
        try {
            user = userService.insertUser(user);
            userService.sendActiveEmail(user);
            writeJson(response, ResultStatusEnum.SUCCESS, "注册成功，前往登录", null);
        } catch (Exception e) {
            e.printStackTrace();
            writeJson(response, ResultStatusEnum.FAILED, "系统错误", null);
        }
    }

    @RequestMapping(path = "/doActivePassport", method = RequestMethod.GET)
    public void doActive(@RequestParam("code") String code,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        CommonResult result = userService.doActive(code);
        if(result.isSuccess()) {
            writeString(response, "<html><head><meta charset=\"UTF-8\"></head><body>成功激活！<a href='/'>跳转到首页</a></body></html>");
        } else {
            writeString(response, "<html><head><meta charset=\"UTF-8\"></head><body>成功失败！原因：" + result.getMsg() + "<a href='/'>跳转到首页</a></body></html>");
        }
    }

    @RequestMapping(path = "/doResetPassword", method = RequestMethod.POST)
    public void resetPassword(@RequestParam("userName") String userName,
                              @RequestParam("password") String password,
                              @RequestParam("newPassword") String newPassword,
                              HttpServletRequest request,
                              HttpServletResponse response) {

    }

    @RequestMapping(path = "/doForgetPassword", method = RequestMethod.POST)
    public void forgetPassword(@RequestParam("userName") String userName,
                               @RequestParam("password") String password,
                               @RequestParam("code") String code,
                               HttpServletRequest request,
                               HttpServletResponse response) {

    }

    @RequestMapping(path = "/sendForgetPasswordEmail", method = RequestMethod.POST)
    public void sendForgetPasswordEmail(@RequestParam("userName") String userName,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {

    }




}
