package com.github.korbeckik.service.controller;

import com.github.korbeckik.service.aspect.Loggable;
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest;
import com.github.korbeckik.service.service.FlashcardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flashcards")
public class FlashcardsController {

    private final FlashcardsService flashcardsService;

    @Loggable
    @PostMapping
    public Mono<ResponseEntity<?>> saveFlashcards(@RequestBody Mono<SaveFlashardsRequest> request) {
        return request.map(flashcardsService::saveFlashcards).map(ResponseEntity::ok);
    }

    @Loggable
    @GetMapping("/list")
    public Mono<ResponseEntity<?>> getFlashcards(@RequestParam(name = "name", required = false) String name) {
        return flashcardsService.getFlashcards(name).collectList().map(ResponseEntity::ok);
    }

}
