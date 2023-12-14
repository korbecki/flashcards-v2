package com.github.korbeckik.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterAcceptRequest(@NotBlank String uid) {
}
