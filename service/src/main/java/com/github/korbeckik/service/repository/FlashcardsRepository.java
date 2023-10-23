package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.FlashcardsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FlashcardsRepository extends R2dbcRepository<FlashcardsEntity, Long> {

    @Query("SELECT * FROM flashcards f WHERE f.created_by=:userId OR f.is_public=true")
    Flux<FlashcardsEntity> findAllByUser(@Param("userId") Long userId);

    @Query("SELECT * FROM flashcards f WHERE f.name LIKE '%'||:name||'%' AND (f.created_by=:userId OR f.is_public=true)")
    Flux<FlashcardsEntity> findAllByUserAndName(@Param("userId") Long userId, @Param("name") String name);
}
