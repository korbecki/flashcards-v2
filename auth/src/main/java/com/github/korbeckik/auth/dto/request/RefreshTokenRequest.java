package com.github.korbeckik.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank String refreshToken, @NotBlank String email) {
}
