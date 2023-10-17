package com.github.korbeckik.auth.i18n;

import lombok.Getter;

@Getter
public enum MessagesEnum {
    JWT_EXCEPTION("jwt.exception.message"),
    UNEXPECTED_ERROR("server.unexpected.exception");

    private final String code;

    MessagesEnum(String code) {
        this.code = code;
    }
}
