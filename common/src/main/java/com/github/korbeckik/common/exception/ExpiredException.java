package com.github.korbeckik.common.exception;

import com.github.korbeckik.common.i18n.MessagesEnum;
import lombok.Getter;

@Getter
public class ExpiredException extends RuntimeException{

    private final MessagesEnum messagesEnum;
    public ExpiredException(MessagesEnum messagesEnum) {
        super();
        this.messagesEnum = messagesEnum;
    }
    public ExpiredException() {
        super();
        this.messagesEnum = MessagesEnum.LINK_EXPIRED_EXCEPTION;
    }
}
