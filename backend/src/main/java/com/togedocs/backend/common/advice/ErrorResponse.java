package com.togedocs.backend.common.advice;

import com.togedocs.backend.common.exception.ErrorCode;
import lombok.Builder;
import org.springframework.http.HttpStatus;


@Builder
public class ErrorResponse {
    private final HttpStatus status;
    private final String msg;

    public static ErrorResponse build(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .msg(errorCode.getMsg())
                .build();
    }
}
