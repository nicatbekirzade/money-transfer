package com.example.cardms.exception.constants;

import lombok.Getter;

public enum ErrorInfo {

    SESSION_EXPIRED("ERR0001", "session_expired"),
    INVALID_TOKEN("ERR0002", "invalid_token"),
    FORBIDDEN("ERR0003", "forbidden"),
    BAD_CREDENTIALS("ERR0004", "bad_credentials"),

    USER_NOT_FOUND("ERR00016", "user_not_found"),
    USER_ALREADY_EXISTS("ERR00016", "user_already_exists"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String messageKey;


    ErrorInfo(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

}
