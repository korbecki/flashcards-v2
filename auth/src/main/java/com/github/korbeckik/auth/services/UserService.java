package com.github.korbeckik.auth.services;

import com.github.korbeckik.auth.entity.RolesEntity;
import com.github.korbeckik.auth.entity.UsersEntity;
import com.github.korbeckik.auth.repository.RolesRepository;
import com.github.korbeckik.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
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
//                    rolesRepository.findByUserId(usersEntity.getId()).collectList().subscribe(rolesList::addAll);
                    usersEntity.setRoles(rolesList);
                    return usersEntity;
                });
    }
}
