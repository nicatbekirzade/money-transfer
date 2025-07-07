package com.example.authms.client;

import com.example.authms.exception.ConstraintsViolationError;
import com.example.authms.exception.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            Map<String, Object> errorAttributes = objectMapper.readValue(response.body().asInputStream(), Map.class);

            int status = (int) errorAttributes.getOrDefault("status", response.status());
            String message = (String) errorAttributes.getOrDefault("message", "An error occurred");
            int errorCode = (int) errorAttributes.getOrDefault("errorCode", 0);
            List<ConstraintsViolationError> errors = (List<ConstraintsViolationError>) errorAttributes.getOrDefault("errors", List.of());
            String path = (String) errorAttributes.getOrDefault("path", "Unknown");

            return new ServiceException(status, message, errorCode, errors, path);
        } catch (IOException e) {
            return new ServiceException(400, "Unknown Client Exception", 0, List.of(), "");
        }
    }
}
