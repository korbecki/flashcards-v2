package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.FlashcardsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardsRepository extends R2dbcRepository<FlashcardsEntity, Long> {
}
