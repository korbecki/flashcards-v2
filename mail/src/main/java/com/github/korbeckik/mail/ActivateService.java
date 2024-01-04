package com.github.korbeckik.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivateService {
    private final ActivateRepository activateRepository;

    public void markAsSend(Long id, String uuid){
        activateRepository.findByUserId(id)
                .ifPresent(entity -> {
                    entity.markAsSent(uuid);
                    activateRepository.save(entity);
                });
    }
}
