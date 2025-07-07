package com.example.transferms.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Object param) {
        super(String.format("Record with '%s' was not found", param));
    }

    public NotFoundException() {
        super("Record was not found");
    }

    public NotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
