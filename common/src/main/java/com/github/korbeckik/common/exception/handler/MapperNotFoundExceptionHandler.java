package com.github.korbeckik.common.exception.handler;

import com.github.korbeckik.common.dto.MessageResponse;
import com.github.korbeckik.common.dto.Response;
import com.github.korbeckik.common.exception.MapperNotFoundException;
import com.github.korbeckik.common.i18n.MessagesEnum;
import com.github.korbeckik.common.i18n.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MapperNotFoundExceptionHandler implements ExceptionHandler {

    public Class<? extends Exception> supportedException() {
        return MapperNotFoundException.class;
    }

    public Response prepareResponse(Throwable ex, Locale local) {
        return new Response(Translator.translate(MessagesEnum.UNEXPECTED_ERROR, local), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
