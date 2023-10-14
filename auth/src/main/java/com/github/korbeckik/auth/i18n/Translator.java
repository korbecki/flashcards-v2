package com.github.korbeckik.auth.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;
    private static LocaleContextResolver localeResolver;

    @Autowired
    public Translator(ResourceBundleMessageSource messageSource, LocaleContextResolver localeResolver) {
        Translator.messageSource = messageSource;
        Translator.localeResolver = localeResolver;
    }

    public static String translate(MessagesEnum msg, Locale locale) {

        return messageSource.getMessage(msg.getCode(), null, locale);
    }

    public static String translate(MessagesEnum msg, Locale locale, String[] args) {
        return messageSource.getMessage(msg.getCode(), args, locale);
    }
}
