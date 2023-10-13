package com.github.korbeckik.auth.repository;

import com.github.korbeckik.auth.entity.UsersEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends R2dbcRepository<UsersEntity, Long> {

    Mono<UsersEntity> findByEmail(String email);
}
