package com.github.korbeckik.auth.exception.handler;

import com.github.korbeckik.auth.dto.response.MessageResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JWTExceptionHandler implements ExceptionHandler{
    public Class<? extends Exception> supportedException() {
        return JwtException.class;
    }

    public MessageResponse prepareResponse(Throwable ex) {
        return new MessageResponse("JWT token expired or is invalid.", HttpStatus.UNAUTHORIZED);
    }
}
