package com.togedocs.backend.common.advice;

import com.togedocs.backend.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
public class ErrorResponse {
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus()).body(
                ErrorResponse.builder()
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
