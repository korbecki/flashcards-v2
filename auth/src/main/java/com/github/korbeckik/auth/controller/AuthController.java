package com.github.korbeckik.auth.controller;

import com.github.korbeckik.auth.dto.request.LoginRequest;
import com.github.korbeckik.auth.dto.request.RegisterRequest;
import com.github.korbeckik.common.dto.MessageResponse;
import com.github.korbeckik.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public Mono<ResponseEntity<?>> register(@RequestBody RegisterRequest registerRequest) {
        return authService.saveUser(registerRequest)
                .map(u -> new MessageResponse("User registered!", HttpStatus.CREATED));
    }


}
