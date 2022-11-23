package com.togedocs.backend.common.advice;

import com.togedocs.backend.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
//        ErrorResponse response = ErrorResponse.build();
//        return ResponseEntity<>()
        System.out.println("BUSINESS EXCEPTION!!");
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        System.out.println("EXCEPTION!!");
        return null;
    }


}
