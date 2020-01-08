package com.panda.family.controller;

import com.alibaba.fastjson.JSON;
import com.panda.family.domain.Deduction;
import com.panda.family.domain.User;
import com.panda.family.service.UserService;
import com.panda.family.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

@Controller
@RequestMapping(path = "/family")
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
    public void getUserInfo(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        User user = userService.queryUserByUserName(ThreadLocalUtil.getUser().getUserName());
        writeJson(response, BaseController.ResultStatusEnum.SUCCESS, "查询成功", JSON.toJSONString(user));
    }

    @RequestMapping(path = "/editUserInfo", method = RequestMethod.POST)
    public void editUserInfo(@RequestBody User user,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        User oldUser = ThreadLocalUtil.getUser();
        user.setUserName(oldUser.getUserName());
        if (user == null) {
            writeJson(response, ResultStatusEnum.FAILED, "用户信息为空", null);
            return;
        }
        if (!RegExpUtil.isNickname(user.getNickname())) {
            writeJson(response, ResultStatusEnum.FAILED, "昵称长度1-18位，汉字、字母、数字组成", null);
            return;
        }
        if (!EmailUtil.checkEmailFormat(user.getEmail())) {
            writeJson(response, ResultStatusEnum.FAILED, "邮箱格式不正确", null);
            return;
        }
        if (!RegExpUtil.isIncome(String.valueOf(user.getIncome()))) {
            writeJson(response, ResultStatusEnum.FAILED, "税后收入为整数或最多两位的小数", null);
            return;
        }
        if (oldUser.getNickname().equals(user.getNickname())
                && oldUser.getEmail().equals(user.getEmail())
                && oldUser.getIncome() == user.getIncome()) {
            writeJson(response, ResultStatusEnum.FAILED, "没有任何变化", null);
            return;
        }
        CommonResult result = userService.updateUser(oldUser, user);
        writeJson(response, BaseController.ResultStatusEnum.SUCCESS, result.getMsg(), null);
    }

    @RequestMapping(path = "/sendActiveEmail", method = RequestMethod.POST)
    public void sendActiveEmail(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        User user = ThreadLocalUtil.getUser();
        userService.sendActiveEmail(user);
        writeJson(response, ResultStatusEnum.SUCCESS, "已发送激活邮件，请注意查收", null);
    }

    @RequestMapping(path = "/createDeduction", method = RequestMethod.POST)
    public void createDeduction(@RequestBody Deduction deduction,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        User user = ThreadLocalUtil.getUser();
        deduction.setUserId(user.getId());
        deduction.setCtime(TimeUtil.getNow());
        deduction.setUtime(TimeUtil.getNow());
        CommonResult result = userService.createDeduction(deduction);
        if (result.isSuccess()) {
            writeJson(response, ResultStatusEnum.SUCCESS, result.getMsg(), null);
        } else {
            writeJson(response, ResultStatusEnum.FAILED, result.getMsg(), null);
        }

    }

    @RequestMapping(path = "/queryDeductionList", method = RequestMethod.GET)
    public void queryDeductionList(HttpServletRequest request,
                                   HttpServletResponse response) throws IOException {
        User user = ThreadLocalUtil.getUser();
        List<Deduction> deductionList = userService.queryDeductionListByUserId(user.getId());
        writeJsonObject(response, ResultStatusEnum.SUCCESS, "查询成功", deductionList);
    }

    @RequestMapping(path = "/removeDeduction", method = RequestMethod.POST)
    public void removeDeduction(@RequestBody Deduction deduction,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        CommonResult result = userService.removeDeduction(deduction.getId());
        if (result.isSuccess()) {
            writeJson(response, ResultStatusEnum.SUCCESS, result.getMsg(), null);
        } else {
            writeJson(response, ResultStatusEnum.FAILED, result.getMsg(), null);
        }
    }
}
