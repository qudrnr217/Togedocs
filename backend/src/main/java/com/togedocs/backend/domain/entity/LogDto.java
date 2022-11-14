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
    private int statusCode;
    private String url;
    // pathVariable 과
    // queryParams 는 url에 포함되어 보내기 때문에 생략
    private String requestBody;
    private String responseBody;
    // requestBody, responseBody 는 fe에서 JSON.parse 해서 사용.

    public static LogDto build(String logTime, int userId, int statusCode, String url, String requestBody, String responseBody) {
        return LogDto.builder()
                .logTime(logTime)
                .userId(userId)
                .statusCode(statusCode)
                .url(url)
                .requestBody(requestBody)
                .responseBody(responseBody)
                .build();
    }
}
