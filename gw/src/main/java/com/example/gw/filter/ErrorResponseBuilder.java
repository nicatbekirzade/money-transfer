package com.example.gw.filter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorResponseBuilder {

    public static Map<String, Object> buildErrorResponse(
            HttpStatus status,
            String message,
            int errorCode,
            List<?> validationErrors,
            String path
    ) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put("status", status.value());
        attributes.put("error", status.getReasonPhrase());
        attributes.put("message", message);
        attributes.put("errorCode", errorCode);
        attributes.put("errors", validationErrors != null ? validationErrors : Collections.emptyList());
        attributes.put("path", path);
        return attributes;
    }
}