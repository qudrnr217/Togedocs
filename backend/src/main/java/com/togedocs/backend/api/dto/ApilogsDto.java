package com.togedocs.backend.api.dto;

import lombok.*;

public class ApilogsDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LogDto {
        private int userId;
        private String logTime;
        private int statusCode;
        private String url;
        private String pathVariable;
        private String queryParams;
        private String requestBody;
        private String responseBody;
    }
}
