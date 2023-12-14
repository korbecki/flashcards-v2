package com.github.korbeckik.auth.dto.request;

import com.github.korbeckik.common.validator.UniqueValue;
import jakarta.validation.constraints.NotBlank;

public record UserNameRequest(
        @NotBlank
        @UniqueValue(entityName = "users", fieldName = "user_name")
        String userName
) {
}
