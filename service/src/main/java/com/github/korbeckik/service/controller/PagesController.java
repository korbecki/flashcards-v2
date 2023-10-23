package com.github.korbeckik.service.controller;

import com.github.korbeckik.service.service.PagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pages")
public class PagesController {

    private final PagesService pagesService;

    @GetMapping("/{flashcardId}")
    public Mono<ResponseEntity<?>> getPagesByFlashcardId(@PathVariable Long flashcardId) {
        return pagesService.getPagesForFlashcardId(flashcardId).collectList().map(ResponseEntity::ok);
    }
}
