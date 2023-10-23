package com.github.korbeckik.service.dto.response;

import java.time.LocalDateTime;

public record PagesListResponse(Long id, String question, String questionImage, String answer, String answerImage,
                                LocalDateTime createdAt) {
}
