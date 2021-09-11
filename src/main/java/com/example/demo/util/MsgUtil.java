package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class MsgUtil {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取抛出异常的信息
     *
     * @param code
     * @param params
     * @return
     */
    public String getMessage(int code, Object... params) {
        Locale locale = LocaleContextHolder.getLocale();
      //  String message = messageSource.getMessage("E_" + code, params, Locale.getDefault());
        String message = messageSource.getMessage("E_" + code, params, locale);
        log.info("code {},message {}", code, message);
        return message;
    }


}
