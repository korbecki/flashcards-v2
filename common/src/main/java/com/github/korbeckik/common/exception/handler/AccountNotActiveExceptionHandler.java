package com.github.korbeckik.common.exception.handler;

import com.github.korbeckik.common.dto.Response;
import com.github.korbeckik.common.exception.AccountNotActiveException;
import com.github.korbeckik.common.exception.BadCredentialsException;
import com.github.korbeckik.common.i18n.MessagesEnum;
import com.github.korbeckik.common.i18n.Translator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AccountNotActiveExceptionHandler implements ExceptionHandler {

    public Class<? extends Exception> supportedException() {
        return AccountNotActiveException.class;
    }

    public Response prepareResponse(Throwable ex, Locale local) {
        if(ex instanceof AccountNotActiveException exception){
            return new Response(Translator.translate(exception.getMessagesEnum(), local), HttpStatus.UNAUTHORIZED);
        }else {
            return new Response(Translator.translate(MessagesEnum.ACTIVATE_ACCOUNT_EXCEPTION, local), HttpStatus.UNAUTHORIZED);
        }
    }
}
