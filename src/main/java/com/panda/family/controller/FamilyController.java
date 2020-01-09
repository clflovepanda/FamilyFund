package com.panda.family.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.panda.family.domain.Family;
import com.panda.family.service.FamilyService;
import com.panda.family.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/family")
public class FamilyController extends BaseController {

    @Autowired
    FamilyService familyService;

    @RequestMapping(path = "/createFamily")
    public void createFamily(@RequestBody Family family,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        if (!RegExpUtil.isFamilyName(family.getFamilyName())) {
            writeJson(response, ResultStatusEnum.FAILED, "家庭名称长度为4-18位，字母或数字组成", null);
            return;
        }
        JSONArray memberJson;
        try {
            memberJson = JSON.parseArray(family.getMember());
        } catch (Exception e) {
            writeJson(response, ResultStatusEnum.FAILED, "家庭成员格式错误", null);
            return;
        }
        family.setCreator(ThreadLocalUtil.getUser().getId());
        family.setMember("[]");
        family.setCtime(TimeUtil.getNow());
        family.setUtime(TimeUtil.getNow());

        CommonResult result = familyService.createFamily(family);
        if (result.isSuccess()) {
            writeJson(response, ResultStatusEnum.SUCCESS, "家庭创建成功", null);
            return;
        } else {
            writeJson(response, ResultStatusEnum.FAILED, result.getMsg(), null);
            return;
        }
    }

    @RequestMapping(path = "/getFamilyList", method = RequestMethod.GET)
    public void getFamilyList(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        List<Family> familyList = familyService.getFamilyList();
        writeJsonObject(response, ResultStatusEnum.SUCCESS, "查询成功", familyList);
    }
}
