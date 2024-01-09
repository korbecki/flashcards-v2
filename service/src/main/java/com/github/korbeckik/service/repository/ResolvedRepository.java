package com.github.korbeckik.service.repository;

import com.github.korbeckik.service.entity.ResolvedEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolvedRepository extends R2dbcRepository<ResolvedEntity, Long> {
}
