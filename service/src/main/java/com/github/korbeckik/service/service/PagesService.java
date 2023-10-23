package com.github.korbeckik.service.service;

import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.common.utils.SecurityUtils;
import com.github.korbeckik.service.dto.response.PagesListResponse;
import com.github.korbeckik.service.repository.PagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class PagesService {
    private final PagesRepository pagesRepository;

    public Flux<PagesListResponse> getPagesForFlashcardId(Long flashcardId) {
        return SecurityUtils.getLoggedUserId()
                .flatMapMany(id -> pagesRepository.findAllByFlashcardId(flashcardId, id))
                .map(page -> MapFactory.map(page, PagesListResponse.class));

    }
}
