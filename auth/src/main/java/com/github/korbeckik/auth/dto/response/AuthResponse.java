package com.github.korbeckik.auth.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(String name, String surname, String userName) {
}
