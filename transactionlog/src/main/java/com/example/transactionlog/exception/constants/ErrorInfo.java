package com.example.transactionlog.exception.constants;

import lombok.Getter;

public enum ErrorInfo {

    USER_NOT_FOUND("ERR00016", "user_not_found");

    @Getter
    private final String code;

    @Getter
    private final String messageKey;


    ErrorInfo(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

}
