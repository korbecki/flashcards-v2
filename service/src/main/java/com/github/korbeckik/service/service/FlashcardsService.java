package com.github.korbeckik.service.service;

import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.common.utils.SecurityUtils;
import com.github.korbeckik.service.dto.request.SaveFlashardsRequest;
import com.github.korbeckik.service.entity.FlashcardsEntity;
import com.github.korbeckik.service.entity.PagesEntity;
import com.github.korbeckik.service.repository.FlashcardsRepository;
import com.github.korbeckik.service.repository.PagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FlashcardsService {

    private final FlashcardsRepository flashcardsRepository;
    private final PagesRepository pagesRepository;

    @Transactional
    public Mono<Boolean> saveFlashcards(SaveFlashardsRequest request) {
        Mono<FlashcardsEntity> flashcardsEntity = saveFlashcardsEntity(request);
        Flux<PagesEntity> pagesEntity = savePagesEntity(flashcardsEntity);
        return pagesEntity.collectList().hasElement();
    }

    private Flux<PagesEntity> savePagesEntity(Mono<FlashcardsEntity> flashcardsEntity) {
        return pagesRepository.saveAll(getPagesEntityFlux(flashcardsEntity));
    }

    private static Flux<PagesEntity> getPagesEntityFlux(Mono<FlashcardsEntity> flashcardsEntity) {
        return flashcardsEntity
                .map(flashcards -> {
                            flashcards
                                    .getPages()
                                    .forEach(page -> page.setFlashcardId(flashcards.getId()));
                            return flashcards.getPages();
                        }
                )
                .flatMapMany(Flux::fromIterable);
    }

    private Mono<FlashcardsEntity> saveFlashcardsEntity(SaveFlashardsRequest request) {
        return SecurityUtils.getLoggedUserId().map(id -> {
                    FlashcardsEntity entity = MapFactory.map(request, FlashcardsEntity.class);
                    entity.setCreatedBy(id);
                    return entity;
                })
                .flatMap(flashcardsRepository::save);
    }

}
