package com.github.korbeckik.service.controller;

import com.github.korbeckik.common.aop.Loggable;
import com.github.korbeckik.service.dto.request.SaveAttemptRequest;
import com.github.korbeckik.service.service.ResolvedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resolve")
public class ResolvedController {
    private final ResolvedService resolvedService;
    @PostMapping
    @Loggable
    public Mono<ResponseEntity<?>> saveAttempt(@RequestBody SaveAttemptRequest saveAttemptRequest) {
        return resolvedService.saveAttempt(saveAttemptRequest).map(resolvedEntity -> {
            if (Objects.nonNull(resolvedEntity)){
                return ResponseEntity.status(204).build();
            }
            return ResponseEntity.internalServerError().build();
        });
    }
}
