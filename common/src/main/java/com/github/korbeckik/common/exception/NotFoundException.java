package com.github.korbeckik.common.exception;

import com.github.korbeckik.common.i18n.MessagesEnum;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{

    private final MessagesEnum messagesEnum;
    public NotFoundException(MessagesEnum messagesEnum) {
        super();
        this.messagesEnum = messagesEnum;
    }
}
