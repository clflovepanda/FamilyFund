package com.panda.family.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    enum ResultStatusEnum {
        SUCCESS(1), FAILED(0);
        private int value;
        ResultStatusEnum(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
    }

    public void writeJson(HttpServletResponse response, ResultStatusEnum resultStatusEnum, String msg, String data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        JSONObject result = new JSONObject();
        result.put("status", resultStatusEnum.getValue());
        result.put("msg", msg);
        result.put("data", data);
        response.getWriter().write(result.toJSONString());
    }

    public void writeJsonObject(HttpServletResponse response, ResultStatusEnum resultStatusEnum, String msg, Object data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        JSONObject result = new JSONObject();
        result.put("status", resultStatusEnum.getValue());
        result.put("msg", msg);
        result.put("data", JSON.toJSONString(data));
        response.getWriter().write(result.toJSONString());
    }

    public void writeString(HttpServletResponse response, String data) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(data);
    }
}
