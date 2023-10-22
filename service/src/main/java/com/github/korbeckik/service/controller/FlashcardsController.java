package com.github.korbeckik.service.controller;

import com.github.korbeckik.common.dto.MessageResponse;
import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.common.utils.SecurityUtils;
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest;
import com.github.korbeckik.service.entity.FlashcardsEntity;
import com.github.korbeckik.service.service.FlashcardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

@RestController("/flashcards")
@RequiredArgsConstructor
public class FlashcardsController {

    private final FlashcardsService flashcardsService;

    @PostMapping
    public Mono<ResponseEntity<?>> saveFlashcards(@RequestBody Mono<SaveFlashardsRequest> request) {
         return request.map(flashcardsService::saveFlashcards).map(ResponseEntity::ok);
    }

}
