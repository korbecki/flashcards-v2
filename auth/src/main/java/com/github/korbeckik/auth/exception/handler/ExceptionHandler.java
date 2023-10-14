package com.github.korbeckik.auth.exception.handler;

import com.github.korbeckik.auth.dto.response.MessageResponse;

import java.util.Locale;

public interface ExceptionHandler {
    Class<? extends Exception> supportedException();

    MessageResponse prepareResponse(Throwable ex, Locale locale);
}
