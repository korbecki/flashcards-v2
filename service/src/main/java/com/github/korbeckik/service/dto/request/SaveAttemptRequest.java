package com.github.korbeckik.service.dto.request;

public record SaveAttemptRequest(Long pageId, String answer, Boolean isCorrect) {
}
