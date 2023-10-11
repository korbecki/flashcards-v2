package com.github.korbeckik.auth.controller;

import com.github.korbeckik.auth.dto.request.LoginRequest;
import com.github.korbeckik.auth.dto.response.AuthResponse;
import com.github.korbeckik.auth.services.AuthService;
import com.github.korbeckik.auth.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("Hello");
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }



}
