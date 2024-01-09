package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.PagesEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PagesRepository extends R2dbcRepository<PagesEntity, Long> {

    @Query("SELECT * FROM pages p " +
            "INNER JOIN flashcards f on f.flashcard_id = p.flashcard_id " +
            "WHERE p.flashcard_id = :flashcardId AND (f.created_by = :userId OR f.is_public = true)" +
            "ORDER BY p.page_id " +
            "LIMIT 1 ")
    Mono<PagesEntity> findFirstByFlashcardsIdAndUserId(@Param("flashcardId") Long flashcardId, @Param("userId") Long userId);

    @Query("SELECT * FROM pages as p" +
            "                WHERE flashcard_id=:flashcard_id" +
            "                    AND page_id NOT IN (SELECT r.page_id FROM resolved r WHERE r.page_id = p.page_id AND r.user_id=:userId)" +
            "                    AND page_id > :previousPageId" +
            "                ORDER BY page_id" +
            "                LIMIT 1")
    Mono<PagesEntity> findByFlashcardsIdAndPreviousPageId(@Param("flashcardId") Long flashcardId, @Param("userId") Long userId, @Param("previousPageId") Long previousPageId);
}
