package com.github.korbeckik.service.controller;

import com.github.korbeckik.common.aop.Loggable;
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest;
import com.github.korbeckik.service.service.FlashcardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class FlashcardsController {

    private final FlashcardsService flashcardsService;

    @Loggable
    @PostMapping
    public Mono<ResponseEntity<?>> saveFlashcards(@RequestBody Mono<SaveFlashardsRequest> request) {
        return request.flatMap(flashcardsService::saveFlashcards).map(it -> {
            if(Boolean.TRUE.equals(it)){
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.internalServerError().build();
        });
    }

    @Loggable
    @GetMapping("/list")
    public Mono<ResponseEntity<?>> getFlashcards(@RequestParam(name = "name", required = false) String name) {
        return flashcardsService.getFlashcards(name).collectList().map(ResponseEntity::ok);
    }


}
