package com.togedocs.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 400 BAD_REQUEST
    USER_INFO_UPDATE_BAD_REQUEST(BAD_REQUEST, "사용자 정보 수정에 실패했습니다."),

    // 401 UNAUTHORIZED
    LOGIN_FAILURE(UNAUTHORIZED, "로그인 정보가 없습니다? 실패했습니다?"),

    // 403 FORBIDDEN
    USER_NOT_ADMIN(FORBIDDEN, "권한이 없습니다."),
    PROJECT_USER_FORBIDDEN(FORBIDDEN, "프로젝트 접근 권한이 없습니다."),


    // 404 NOT_FOUND
    PROJECT_NOT_FOUND(NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
    ROW_NOT_FOUND(NOT_FOUND, "행을 찾을 수 없습니다."),
    COL_NOT_FOUND(NOT_FOUND, "열을 찾을 수 없습니다."),
    CELL_NOT_FOUND(NOT_FOUND, "셀을 찾을 수 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
    PROJECT_USER_NOT_FOUND(NOT_FOUND, "유저가 프로젝트에 속해있지 않습니다."),

    // 500
    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "요청을 처리할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
