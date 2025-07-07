package com.example.userms.exception.constants;

import lombok.Getter;

public enum ErrorInfo {

    USER_VERIFICATION_CODE_NOT_FOUND("ERR0001", "user_verification_code_not_found"),
    ROLE_NOT_FOUND("ERR0002", "role_not_found"),
    USER_NOT_FOUND("ERR0007", "user_not_found"),
    ONE_ROLE_REQUIRED("ERR0009", "one_role_required"),
    PASSWORD_RECOVER_NOT_ALLOWED("ERR00010", "password_recovery_not_allowed"),
    PASSWORD_REQUIREMENT("ERR00011", "password_requirements"),
    FORBIDDEN("ERR00012", "forbidden");

    @Getter
    private final String code;

    @Getter
    private final String messageKey;

    ErrorInfo(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

}
