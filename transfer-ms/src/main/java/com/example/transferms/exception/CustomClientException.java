package com.example.transferms.exception;

import lombok.Getter;

@Getter
public class CustomClientException extends RuntimeException {

    private Integer code = 400;

    public CustomClientException(Integer status, String message) {
        super(message);
        this.code = status;
    }

    public CustomClientException(String message) {
        super(message);
    }
}
