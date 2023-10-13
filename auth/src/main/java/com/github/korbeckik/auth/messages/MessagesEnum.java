package com.github.korbeckik.auth.messages;

import lombok.Getter;

public enum MessagesEnum {
    MESSAGE("2");


    @Getter
    private final String code;

    MessagesEnum(String code) {
        this.code = code;
    }
}
