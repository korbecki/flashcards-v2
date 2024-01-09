package com.github.korbeckik.service.service;

import com.github.korbeckik.common.mapper.MapFactory;
import com.github.korbeckik.common.utils.SecurityUtils;
import com.github.korbeckik.service.dto.request.SaveAttemptRequest;
import com.github.korbeckik.service.dto.response.FlashcardsListResponse;
import com.github.korbeckik.service.entity.ResolvedEntity;
import com.github.korbeckik.service.repository.ResolvedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ResolvedService {
    private final ResolvedRepository resolvedRepository;

    public Mono<ResolvedEntity> saveAttempt(SaveAttemptRequest request){
        return SecurityUtils.getLoggedUserId()
                .map(it -> {
                    ResolvedEntity entity = MapFactory.map(request, ResolvedEntity.class);
                    entity.setUserId(it);
                    return entity;
                }).flatMap(resolvedRepository::save);
    }
}
