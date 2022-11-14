package com.togedocs.backend.api.dto;

import lombok.*;

public class ApilogsRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NewLogDto {
        private int userId;
        // logTime은 service에서 생성
        private int statusCode;
        private String url;
        private String requestBody;
        private String responseBody;
    }
}
