package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.PagesEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagesRepository extends R2dbcRepository<PagesEntity, Long> {
}
