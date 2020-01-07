package com.panda.family.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult<T> {

    private CommonResultEnum type;
    private String msg;
    private T data;

    public CommonResult(CommonResultEnum type) {
        this.type = type;
        this.msg = "";
        this.data = null;
    }

    public CommonResult(CommonResultEnum type, String msg) {
        this.type = type;
        this.msg = msg;
        this.data = null;
    }

    public CommonResult(CommonResultEnum type, String msg, T data) {
        this.type = type;
        this.msg = msg;
        this.data = data;
    }

    public static CommonResult successCommonResult() {
        return new CommonResult(CommonResultEnum.SUCCESS);
    }
    public static CommonResult successCommonResult(String msg) {
        return new CommonResult(CommonResultEnum.SUCCESS, msg);
    }
    public static <S> CommonResult<S> successCommonResult(String msg, S data) {
        return new CommonResult<S>(CommonResultEnum.SUCCESS, msg, data);
    }

    public static CommonResult failedCommonResult() {
        return new CommonResult(CommonResultEnum.FAILED);
    }
    public static CommonResult failedCommonResult(String msg) {
        return new CommonResult(CommonResultEnum.FAILED, msg);
    }
    public static <S> CommonResult<S> failedCommonResult(String msg, S data) {
        return new CommonResult<S>(CommonResultEnum.FAILED, msg, data);
    }
}
