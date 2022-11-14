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
    private int userId;
    private String logTime;
    private int statusCode;
    private String url;
    private String pathVariable;
    private String queryParams;
    private String requestBody;
    private String responseBody;
    // pathVariable, queryParams, requestBody, responseBody 모두 fe에서 JSON.parse 해서 사용.

    public static LogDto build(int userId, String logTime, int statusCode, String url, String pathVariable, String queryParams, String requestBody, String responseBody){
        return LogDto.builder()
                .userId(userId)
                .logTime(logTime)
                .statusCode(statusCode)
                .url(url)
                .pathVariable(pathVariable)
                .queryParams(queryParams)
                .requestBody(requestBody)
                .responseBody(responseBody)
                .build();
    }
}
