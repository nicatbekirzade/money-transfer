package com.example.authms.exception;

public class InvalidAuthProviderException extends RuntimeException {

    public InvalidAuthProviderException(String message) {
        super(message);
    }
}