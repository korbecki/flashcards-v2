package com.github.korbeckik.common.config;

import com.github.korbeckik.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(
                        exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION)
                )
                .map(this::removeBracelets)
                .filter(s -> s.startsWith(Constants.JWT_TOKEN_PREFIX))
                .map(s -> s.replaceFirst(Constants.JWT_TOKEN_PREFIX, Constants.EMPTY_STRING))
                .map(BearerToken::new);
    }

    private String removeBracelets(String str) {
        if (StringUtils.startsWith(str, "[")) {
            return StringUtils.substringBetween(str, "[", "]");
        }
        return str;
    }
}
