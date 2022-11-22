package com.togedocs.backend.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ApilogsRequest {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AddLogRequest {
        @NotEmpty
        private String userName;

        @NotEmpty
        private String method;
        @NotEmpty
        private String url;
        private String requestBody;

        @Min(value = 100)
        @Max(value = 999)
        private Integer statusCode;
        private String responseBody;
    }
}
