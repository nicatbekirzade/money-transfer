package com.example.transactionlog.exception;

public class ClientException extends RuntimeException {

    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }
}
