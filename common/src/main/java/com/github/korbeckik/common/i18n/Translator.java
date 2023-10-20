package com.github.korbeckik.common.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String translate(MessagesEnum msg, Locale locale) {

        return messageSource.getMessage(msg.getCode(), null, locale);
    }

    public static String translate(MessagesEnum msg, Locale locale, String[] args) {
        return messageSource.getMessage(msg.getCode(), args, locale);
    }
}
