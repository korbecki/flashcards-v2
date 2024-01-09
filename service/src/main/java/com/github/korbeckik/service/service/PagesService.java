package com.github.korbeckik.service.service;

import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.common.utils.SecurityUtils;
import com.github.korbeckik.service.dto.response.PagesListResponse;
import com.github.korbeckik.service.entity.PagesEntity;
import com.github.korbeckik.service.repository.PagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PagesService {
    private final PagesRepository pagesRepository;

    public Mono<PagesEntity> getPageByFlashcardId(Long flashcardId) {
        return SecurityUtils.getLoggedUserId()
                .flatMap(id -> pagesRepository.findFirstByFlashcardsIdAndUserId(flashcardId, id));

    }
    public Mono<PagesEntity> getPageByFlashcardIdAndPreviousPageId(Long flashcardId, Long previousPageId) {
        return SecurityUtils.getLoggedUserId()
                .flatMap(id -> pagesRepository.findByFlashcardsIdAndPreviousPageId(flashcardId, id, previousPageId))
                .switchIfEmpty(getPageByFlashcardId(flashcardId));
    }
}
