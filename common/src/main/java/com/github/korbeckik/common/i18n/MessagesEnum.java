package com.github.korbeckik.common.i18n;

import lombok.Getter;

@Getter
public enum MessagesEnum {
    JWT_EXCEPTION("jwt.exception.message"),
    UNEXPECTED_ERROR("server.unexpected.exception"),
    ACTIVATE_NOT_FOUND_EXCEPTION("activate.not.found.exception"),
    ACTIVATE_LINK_EXPIRED_EXCEPTION("activate.link.expired.exception"),
    NOT_FOUND_EXCEPTION("not.found.exception"),
    LINK_EXPIRED_EXCEPTION("link.expired.exception"),
    BAD_CREDENTIALS_EXCEPTION("bad.credentials.exception"),
    ACTIVATE_ACCOUNT_EXCEPTION("activate.account.exception");

    private final String code;

    MessagesEnum(String code) {
        this.code = code;
    }
}
