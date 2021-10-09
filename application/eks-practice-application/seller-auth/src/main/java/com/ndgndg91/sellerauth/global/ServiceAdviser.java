package com.ndgndg91.sellerauth.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceAdviser {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(ServiceException e) {
        return ResponseEntity
            .status(e.getStatusCode())
            .body(new ApiError(e.getErrorCode(), e.getErrorMessage()));
    }
}
