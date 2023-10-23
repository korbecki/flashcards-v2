package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.PagesEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PagesRepository extends R2dbcRepository<PagesEntity, Long> {

    @Query("SELECT * FROM pages p " +
            "INNER JOIN flashcards f on f.flashcard_id = p.flashcard_id " +
            "WHERE p.flashcard_id = :flashcardId AND (f.created_by = :userId OR f.is_public = true)")
    Flux<PagesEntity> findAllByFlashcardId(@Param("flashcardId") Long flashcardId, @Param("userId") Long userId);
}
