package com.togedocs.backend.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LogDto {

    private String logTime;
    private int userId;

    private String method;
    private String url;
    private String requestBody;

    private int statusCode;
    private String responseBody;

    // pathVariable 과 queryParams 는 url에 포함되어 보내기 때문에 생략
    // requestBody, responseBody 는 fe에서 JSON.parse 해서 사용.

    public static LogDto build(String logTime, int userId, String method, String url, String requestBody, int statusCode, String responseBody) {
        return LogDto.builder()
                .logTime(logTime)
                .userId(userId)
                .method(method)
                .url(url)
                .requestBody(requestBody)
                .statusCode(statusCode)
                .responseBody(responseBody)
                .build();
    }
}
