package com.github.korbeckik.auth.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.korbeckik.auth.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNullApi;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final Set<ExceptionHandler> exceptionHandlerList;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        MessageResponse exceptionHandler = exceptionHandlerList.stream()
                .filter(handler -> handler.supportedException().isInstance(ex))
                .findFirst()
                .map(handler -> handler.prepareResponse(ex))
                .orElse(prepareDefaultResponse());

        exchange.getResponse().setStatusCode(exceptionHandler.getStatusCode());
        return write(exchange.getResponse(), exceptionHandler.getBody());
    }


    private <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        return httpResponse
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(object));
                    } catch (Exception ex) {
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }

    private MessageResponse prepareDefaultResponse() {
        return new MessageResponse("Unexpected Error!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}