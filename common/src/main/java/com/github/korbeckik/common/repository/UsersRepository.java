package com.github.korbeckik.common.repository;

import com.github.korbeckik.common.entity.UsersEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends R2dbcRepository<UsersEntity, Long> {

    Mono<UsersEntity> findByEmail(String email);
}
