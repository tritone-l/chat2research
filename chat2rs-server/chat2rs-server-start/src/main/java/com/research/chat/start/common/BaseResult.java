package com.research.chat.start.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 前后端交互基础返回值
 * @Author：tritone
 * @Date：2024/1/25 23:19
 */
@Slf4j
@Data
public class BaseResult<T> {
    private int code;
    private String msg;
    private T data;

    public BaseResult() {
    }
    public BaseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static BaseResult success(Object data) {
         return new BaseResult(0, "success", data);
    }
    public static BaseResult success() {
        return new BaseResult(0, "success", null);
    }
    public static BaseResult error(String msg) {
        return new BaseResult(1, msg, null);
    }
    public static BaseResult error(int code, String msg) {
        return new BaseResult(code, msg, null);
    }
}
