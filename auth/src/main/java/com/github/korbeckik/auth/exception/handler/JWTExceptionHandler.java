package com.github.korbeckik.auth.exception.handler;

import com.github.korbeckik.auth.dto.response.MessageResponse;
import com.github.korbeckik.auth.i18n.MessagesEnum;
import com.github.korbeckik.auth.i18n.Translator;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class JWTExceptionHandler implements ExceptionHandler {

    public Class<? extends Exception> supportedException() {
        return JwtException.class;
    }

    public MessageResponse prepareResponse(Throwable ex, Locale local) {
        return new MessageResponse(Translator.translate(MessagesEnum.JWT_EXCEPTION, local), HttpStatus.UNAUTHORIZED);
    }
}
