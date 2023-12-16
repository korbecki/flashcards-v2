package com.github.korbeckik.auth.services;

import com.github.korbeckik.auth.amqp.RabbitMQProducer;
import com.github.korbeckik.auth.dto.request.LoginRequest;
import com.github.korbeckik.auth.dto.request.RefreshTokenRequest;
import com.github.korbeckik.auth.dto.request.RegisterAcceptRequest;
import com.github.korbeckik.auth.dto.request.RegisterRequest;
import com.github.korbeckik.auth.dto.response.AuthResponse;
import com.github.korbeckik.auth.entity.ActivateEntity;
import com.github.korbeckik.auth.mapper.RegisterRequestToUsersEntityMapper;
import com.github.korbeckik.auth.repository.ActivateRepository;
import com.github.korbeckik.common.dto.Response;
import com.github.korbeckik.common.entity.UsersEntity;
import com.github.korbeckik.common.exception.AccountNotActiveException;
import com.github.korbeckik.common.exception.BadCredentialsException;
import com.github.korbeckik.common.exception.ExpiredException;
import com.github.korbeckik.common.exception.NotFoundException;
import com.github.korbeckik.common.i18n.MessagesEnum;
import com.github.korbeckik.common.repository.UsersRepository;
import com.github.korbeckik.common.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final ActivateRepository activateRepository;
    private final RabbitMQProducer rabbitMQProducer;


    public Mono<ResponseEntity<?>> login(LoginRequest loginRequest) {
        return usersRepository.findByEmail(loginRequest.email())
                .switchIfEmpty(Mono.error(new NotFoundException(MessagesEnum.NOT_FOUND_EXCEPTION)))
                .filter(it -> passwordEncoder.matches(loginRequest.password(), it.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException(MessagesEnum.NOT_FOUND_EXCEPTION)))
                .publishOn(Schedulers.boundedElastic())
                .filter(it -> Boolean.TRUE.equals(activateRepository.findById(it.getActivateId())
                        .map(ActivateEntity::getIsActivated)
                        .filter(bool -> bool)
                        .block()))
                .switchIfEmpty(Mono.error(new AccountNotActiveException(MessagesEnum.ACTIVATE_ACCOUNT_EXCEPTION)))
                .map(usersEntity -> {
                    usersEntity.createRefreshToken();
                    return usersEntity;
                })
                .flatMap(usersRepository::save)
                .map(usersEntity -> new Response(AuthResponse.builder()
                        .name(usersEntity.getUserName())
                        .surname(usersEntity.getSurname())
                        .userName(usersEntity.getUserName())
                        .jwtToken(jwtService.generateToken(usersEntity.getEmail()))
                        .refreshToken(usersEntity.getRefreshToken())
                        .build(), HttpStatus.OK));
    }

    public Mono<UsersEntity> saveUser(RegisterRequest registerRequest) {
        return usersRepository.save(RegisterRequestToUsersEntityMapper.INSTANCE.sourceToDestination(encodePassword(registerRequest)))
                .doOnNext(rabbitMQProducer::sendMessage);
    }

    public Mono<ActivateEntity> registerAccept(RegisterAcceptRequest request) {
        return activateRepository.findByCode(request.uid())
                .switchIfEmpty(Mono.error(new NotFoundException(MessagesEnum.ACTIVATE_NOT_FOUND_EXCEPTION)))
                .filter(activateEntity -> activateEntity.getExpiredAt().isAfter(LocalDateTime.now()))
                .switchIfEmpty(Mono.error(new ExpiredException()))
                .flatMap(it -> activateRepository.save(it.activate()));
    }

    public Mono<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return usersRepository.findByEmail(refreshTokenRequest.email())
                .switchIfEmpty(Mono.error(new NotFoundException(MessagesEnum.NOT_FOUND_EXCEPTION)))
                .filter(entity -> entity.getRefreshTokenExpiration().isAfter(LocalDateTime.now()))
                .switchIfEmpty(Mono.error(new ExpiredException(MessagesEnum.NOT_FOUND_EXCEPTION)))
                .flatMap(createRefreshToken())
                .map(createJwtToken());
    }

    private Function<UsersEntity, AuthResponse> createJwtToken() {
        return it -> AuthResponse.builder()
                .name(it.getName())
                .surname(it.getSurname())
                .userName(it.getUserName())
                .jwtToken(jwtService.generateToken(it.getEmail()))
                .refreshToken(it.getRefreshToken())
                .build();
    }


    private Function<UsersEntity, Mono<? extends UsersEntity>> createRefreshToken() {
        return it -> {
            it.createRefreshToken();
            return usersRepository.save(it);
        };
    }


    private RegisterRequest encodePassword(RegisterRequest registerRequest) {
        String encryptedPassword = passwordEncoder.encode(registerRequest.password());
        return new RegisterRequest(registerRequest.name(), registerRequest.surname(),
                registerRequest.userName(), encryptedPassword, registerRequest.email());
    }
}
