package com.togedocs.backend.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

        @NotEmpty
        private int statusCode;
        private String responseBody;
    }
}
