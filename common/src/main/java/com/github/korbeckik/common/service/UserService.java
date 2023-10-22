package com.github.korbeckik.common.service;

import com.github.korbeckik.common.entity.RolesEntity;
import com.github.korbeckik.common.entity.UsersEntity;
import com.github.korbeckik.common.repository.RolesRepository;
import com.github.korbeckik.common.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    Mono<UsersEntity> findByEmail(String email) {
        return usersRepository.findByEmail(email)
                .publishOn(Schedulers.boundedElastic())
                .map(usersEntity -> {
                    List<RolesEntity> rolesList = rolesRepository.findByUserId(usersEntity.getId()).collectList().block();
                    usersEntity.setRoles(rolesList);
                    return usersEntity;
                });
    }
}
