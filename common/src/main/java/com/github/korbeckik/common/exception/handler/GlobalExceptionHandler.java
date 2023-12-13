package com.github.korbeckik.common.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.korbeckik.common.dto.Response;
import com.github.korbeckik.common.i18n.MessagesEnum;
import com.github.korbeckik.common.i18n.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;
    private final Set<ExceptionHandler> exceptionHandlerList;
    private final LocaleContextResolver localeContextResolver;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        Locale locale = resolveLocale(exchange);
        Response exceptionHandler = exceptionHandlerList.stream()
                .filter(handler -> handler.supportedException().isInstance(ex))
                .findFirst()
                .map(handler -> handler.prepareResponse(ex, locale))
                .orElse(prepareDefaultResponse(locale, ex));
        exchange.getResponse().setStatusCode(exceptionHandler.getStatusCode());
        return write(exchange, Objects.requireNonNull(exceptionHandler.getBody()));
    }

    private Locale resolveLocale(ServerWebExchange exchange) {
        Locale locale = localeContextResolver.resolveLocaleContext(exchange).getLocale();
        return Objects.isNull(locale) ? Locale.ENGLISH : locale;
    }

    private Mono<Void> write(ServerWebExchange exchange, Response.Body object) {

        var db = Mono.just(exchange.getResponse().bufferFactory().wrap(convertToBytes(object)));
        return exchange.getResponse().writeWith(db);
    }

    private byte[] convertToBytes(Response.Body obj) {
        try{
            return objectMapper.writeValueAsBytes(obj);
        }catch (JsonProcessingException e){
            return "Internal server error".getBytes(StandardCharsets.UTF_8);
        }
    }

    private Response prepareDefaultResponse(Locale locale, Throwable ex) {
        log.error(ex);
        return new Response(Translator.translate(MessagesEnum.UNEXPECTED_ERROR, locale), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}