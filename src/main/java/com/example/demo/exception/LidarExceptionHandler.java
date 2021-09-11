package com.example.demo.exception;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.util.MsgUtil;
import com.example.demo.util.Result;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LidarExceptionHandler {
    @Autowired
    private MsgUtil msgUtil;

    @ExceptionHandler(LidarException.class)
    public Result handlerLidarException(LidarException e) {
        Result result = new Result();
        System.out.println(e.getCode()+"   "+e.getErrorMessage());
        result.put("code", e.getCode());
        if (StringUtils.isNotEmpty(e.getErrorMessage())) {
            result.put("msg", e.getErrorMessage());
        } else {
            String message = msgUtil.getMessage(e.getCode(), e.getParams());
            System.out.println(message);
            if (StringUtils.isEmpty(message)) {
                message = e.getErrorMessage();
            }
            result.put("msg", message);
        }
        //     Result rr=result.error(e.getCode(),e.getErrorMessage());
        return result;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result handleShiroException(Exception e) {
        Result result = new Result();
        result.put("code",500);
        result.put("msg","您无权访问该信息");
        return result;
    }

}
