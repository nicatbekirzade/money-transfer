package com.example.transferms.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    private int status;
    private String message;
    private int errorCode;
    private List<ConstraintsViolationError> errors;
    private String path;
}
