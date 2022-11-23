package com.togedocs.backend.common.exception;

public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
