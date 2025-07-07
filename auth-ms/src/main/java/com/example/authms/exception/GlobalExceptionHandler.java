
package com.example.authms.exception;

import static com.example.authms.exception.constants.HttpResponseConstants.ERROR;
import static com.example.authms.exception.constants.HttpResponseConstants.ERRORS;
import static com.example.authms.exception.constants.HttpResponseConstants.ERROR_CODE;
import static com.example.authms.exception.constants.HttpResponseConstants.MESSAGE;
import static com.example.authms.exception.constants.HttpResponseConstants.PATH;
import static com.example.authms.exception.constants.HttpResponseConstants.STATUS;

import com.example.authms.exception.constants.ErrorInfo;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        ex.printStackTrace();
        log.error("UNKNOWN EXCEPTION {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(InvalidAuthProviderException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            InvalidAuthProviderException ex, WebRequest request) {
        log.warn("Invalid Auth Provider {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            AlreadyExistsException ex, WebRequest request) {
        log.warn("ALREADY EXISTS {}", (Object[]) ex.getStackTrace());
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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Map<String, Object>> handle(AccessDeniedException ex, WebRequest request) {

        log.info("Authorization Error {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.FORBIDDEN, ErrorInfo.FORBIDDEN.getMessageKey());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Map<String, Object>> handle(BadCredentialsException ex, WebRequest request) {

        log.info("Auth Error {}", ex.getMessage());
        return ofType(request, HttpStatus.UNAUTHORIZED, ErrorInfo.BAD_CREDENTIALS.getMessageKey());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            ExpiredJwtException ex, WebRequest request) {
        log.warn("AUTHORIZATION TOKEN EXPIRED {}", ex.getMessage());
        return ofType(request, HttpStatus.UNAUTHORIZED, ErrorInfo.SESSION_EXPIRED.getMessageKey());
    }

    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<Map<String, Object>> handle(
            SignatureException ex, WebRequest request) {
        log.warn("INVALID AUTHORIZATION TOKEN SIGNATURE {}", (Object[]) ex.getStackTrace());
        return ofType(request, HttpStatus.UNAUTHORIZED, ex.getMessage());
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

