package com.github.korbeckik.auth.config;

import com.github.korbeckik.auth.services.JWTService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class AuthManager implements ReactiveAuthenticationManager {

    private final JWTService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String email = jwtService.extractUsername(auth.getCredentials());
                    return userDetailsService.findByUsername(email).mapNotNull(u -> {
                        if (jwtService.validateToken(auth.getCredentials(), u)) {
                            return new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(), u.getAuthorities());
                        }
                        return null;
                    }).switchIfEmpty(Mono.error(new JwtException("User not found!")));
                });

    }
}
