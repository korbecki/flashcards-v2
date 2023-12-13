package com.github.korbeckik.auth.dto.request;

import com.github.korbeckik.common.validator.UniqueValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        @UniqueValue(entityName = "users", fieldName = "user_name")
        String userName,
        @NotBlank
        String password,
        @Email
        String email) {
}
