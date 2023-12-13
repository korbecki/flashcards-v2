package com.github.korbeckik.common.exception.handler;

import com.github.korbeckik.common.dto.MessageResponse;
import com.github.korbeckik.common.dto.Response;

import java.util.Locale;

public interface ExceptionHandler {
    Class<? extends Exception> supportedException();

    Response prepareResponse(Throwable ex, Locale locale);
}
