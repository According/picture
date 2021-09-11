package com.example.demo.util;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    public Result() {
        put("code", 0);
        put("msg", "success");
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result ok(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        return result;
    }

    public Result put(String key, Object obj) {
        super.put(key, obj);
        return this;
    }
    public Result putData(Object obj) {
        super.put("data", obj);
        return this;
    }
}

