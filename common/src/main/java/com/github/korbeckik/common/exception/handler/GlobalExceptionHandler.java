package com.github.korbeckik.common.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.korbeckik.common.dto.MessageResponse;
import com.github.korbeckik.common.i18n.MessagesEnum;
import com.github.korbeckik.common.i18n.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final Set<ExceptionHandler> exceptionHandlerList;
    private final LocaleContextResolver localeContextResolver;

    @Override

    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Locale locale = resolveLocale(exchange);
        MessageResponse exceptionHandler = exceptionHandlerList.stream()
                .filter(handler -> handler.supportedException().isInstance(ex))
                .findFirst()
                .map(handler -> handler.prepareResponse(ex, locale))
                .orElse(prepareDefaultResponse(locale));
        exchange.getResponse().setStatusCode(exceptionHandler.getStatusCode());
        return write(exchange.getResponse(), Objects.requireNonNull(exceptionHandler.getBody()));
    }

    private Locale resolveLocale(ServerWebExchange exchange) {
        Locale locale = localeContextResolver.resolveLocaleContext(exchange).getLocale();
        return Objects.isNull(locale) ? Locale.ENGLISH : locale;
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

    private MessageResponse prepareDefaultResponse(Locale locale) {
        return new MessageResponse(Translator.translate(MessagesEnum.UNEXPECTED_ERROR, locale), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}