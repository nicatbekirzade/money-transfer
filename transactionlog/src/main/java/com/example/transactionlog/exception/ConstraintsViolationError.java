package com.example.transactionlog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConstraintsViolationError {

    private String property;
    private String message;
}
