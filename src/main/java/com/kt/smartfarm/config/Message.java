package com.kt.smartfarm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Message {
    @Autowired
    private MessageSource messageSource;

    @Value("smartfarm.lang_tag:ko-KR")
    public String langTag;
    @Bean
    public MessageSourceAccessor getMessageSource() {

        return new MessageSourceAccessor(messageSource, Locale.forLanguageTag(langTag));
    }

    public String getMessage(String key){
        return getMessageSource().getMessage(key, Locale.forLanguageTag(langTag));
    }

    public String getMessage(String key, Object[] objs){
        return getMessageSource().getMessage(key, objs, Locale.forLanguageTag(langTag));
    }
}
