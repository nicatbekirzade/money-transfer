
package com.example.cardms.exception;

import static com.example.cardms.exception.constants.HttpResponseConstants.ERROR;
import static com.example.cardms.exception.constants.HttpResponseConstants.ERRORS;
import static com.example.cardms.exception.constants.HttpResponseConstants.ERROR_CODE;
import static com.example.cardms.exception.constants.HttpResponseConstants.MESSAGE;
import static com.example.cardms.exception.constants.HttpResponseConstants.PATH;
import static com.example.cardms.exception.constants.HttpResponseConstants.STATUS;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    private static final String ARGUMENT_VALIDATION_FAILED = "Argument validation failed";

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, Object>> handle(
            Exception ex, WebRequest request) {
        log.error("UNKNOWN EXCEPTION {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            AlreadyExistsException ex, WebRequest request) {
        log.warn("ALREADY EXISTS {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CustomValidationException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            CustomValidationException ex, WebRequest request) {
        log.warn("Validation Exception: {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CardGenerationException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            CardGenerationException ex, WebRequest request) {
        log.warn("Card Generation Failed {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            ServiceException ex, WebRequest request) {
        log.warn("Feign Exception: {}, on target path {}", ex.getMessage(), ex.getPath());
        return ofType(request, HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getErrors(), ex.getErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            NotFoundException ex, WebRequest request) {
        log.warn("NOT FOUND {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(ClientException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            ClientException ex, WebRequest request) {
        log.warn("Client did`t return success result {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.warn("Method arguments are not valid {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MismatchedInputException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MismatchedInputException ex, WebRequest request) {
        log.warn("Mismatched inout {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ConstraintsViolationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        return ofType(request, HttpStatus.BAD_REQUEST, ARGUMENT_VALIDATION_FAILED, validationErrors, 0);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message, int errorCode) {
        return ofType(request, status, message, Collections.emptyList(), errorCode);
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message) {
        return ofType(request, status, message, Collections.emptyList(), 0);
    }

    private ResponseEntity<Map<String, Object>> ofType(
            WebRequest request, HttpStatus status, String message, List<ConstraintsViolationError> validationErrors, int errorCode) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(STATUS, status.value());
        attributes.put(ERROR, status.getReasonPhrase());
        attributes.put(MESSAGE, message);
        attributes.put(ERROR_CODE, errorCode);
        attributes.put(ERRORS, validationErrors);
        attributes.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }

}

