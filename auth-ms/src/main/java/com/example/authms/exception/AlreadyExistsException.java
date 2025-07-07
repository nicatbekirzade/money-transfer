package com.example.authms.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(Object param) {
        super(String.format("Record with '%s' already exists", param));
    }

    public AlreadyExistsException() {
        super("Record already exists");
    }

    public AlreadyExistsException(String message, Throwable ex) {
        super(message, ex);
    }
}