package com.akvelon.facebook.exception;

import lombok.Getter;

@Getter
public abstract class RestException extends RuntimeException {
    private static final String USER_DEFAULT_MESSAGE = "Ошибка на стороне сервера";
    private static final String MESSAGE_CODE = "server.error";

    private final String messageCode;
    private final String sysMessage;

    protected RestException(String sysMessage) {
        super(USER_DEFAULT_MESSAGE);
        this.messageCode = MESSAGE_CODE;
        this.sysMessage = sysMessage;
    }

}
