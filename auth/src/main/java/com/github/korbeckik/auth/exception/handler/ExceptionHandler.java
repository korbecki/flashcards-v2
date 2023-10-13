package com.github.korbeckik.auth.exception.handler;

import com.github.korbeckik.auth.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface ExceptionHandler {
    Class<? extends Exception> supportedException();
    MessageResponse prepareResponse(Throwable ex);
}
