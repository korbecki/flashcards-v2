package com.github.korbeckik.auth.services;

import com.github.korbeckik.auth.dto.request.LoginRequest;
import com.github.korbeckik.auth.dto.request.RegisterRequest;
import com.github.korbeckik.auth.dto.response.AuthResponse;
import com.github.korbeckik.auth.mapper.RegisterRequestToUsersEntityMapper;
import com.github.korbeckik.common.entity.UsersEntity;
import com.github.korbeckik.common.repository.UsersRepository;
import com.github.korbeckik.common.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveUserDetailsService userDetailsService;
    private final UsersRepository usersRepository;


    public Mono<ResponseEntity<?>> login(@RequestBody LoginRequest loginRequest) {
        Mono<UserDetails> user = userDetailsService.findByUsername(loginRequest.email());
        return user.flatMap(userDetails -> passwordEncoder.matches(loginRequest.password(), userDetails.getPassword())
                ? generateSuccessLoginResponse(userDetails)
                : generateFailureLoginResponse()
        ).switchIfEmpty(generateFailureLoginResponse());
    }

    public Mono<UsersEntity> saveUser(RegisterRequest registerRequest) {
        return usersRepository.save(RegisterRequestToUsersEntityMapper.INSTANCE.sourceToDestination(encodePassword(registerRequest)));
    }

    private Mono<ResponseEntity<AuthResponse>> generateSuccessLoginResponse(UserDetails userDetails) {
        AuthResponse authResponse = AuthResponse.builder()
                .name(userDetails.getUsername())
                .build();
        return Mono.just(ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtService.generateToken(userDetails.getUsername()))
                .body(authResponse));
    }

    private Mono<ResponseEntity<?>> generateFailureLoginResponse() {
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private RegisterRequest encodePassword(RegisterRequest registerRequest) {
        String encryptedPassword = passwordEncoder.encode(registerRequest.password());
        return new RegisterRequest(registerRequest.name(), registerRequest.surname(),
                registerRequest.userName(), encryptedPassword, registerRequest.email());
    }
}
