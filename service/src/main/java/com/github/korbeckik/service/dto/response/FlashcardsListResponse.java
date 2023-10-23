package com.github.korbeckik.service.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class FlashcardsListResponse {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private Boolean isPublic;
    private String createdBy;
    private LocalDateTime createdAt;
}
