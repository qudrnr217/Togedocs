package com.togedocs.backend.api.dto;

import lombok.*;

public class ApilogsRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LogDto {
        // logTime은 service에서 생성
        private String userName;

        private String method;
        private String url;
        private String requestBody;

        private int statusCode;
        private String responseBody;
    }
}
