package com.github.korbeckik.auth.repository;

import com.github.korbeckik.auth.entity.ActivateEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ActivateRepository  extends R2dbcRepository<ActivateEntity, Long> {


    Mono<ActivateEntity> findByCode(String code);
}
