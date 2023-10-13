package com.github.korbeckik.auth.repository;

import com.github.korbeckik.auth.entity.RolesEntity;
import com.github.korbeckik.auth.entity.UsersEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RolesRepository extends R2dbcRepository<RolesEntity, Long> {

    @Query("select * from roles where role_id in(select ur.role_id from users_roles ur where ur.user_id=:userId)")
    Flux<RolesEntity> findByUserId(Long userId);
}
