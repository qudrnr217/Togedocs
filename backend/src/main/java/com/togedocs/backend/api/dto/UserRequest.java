package com.togedocs.backend.api.dto;

import lombok.*;

public class UserRequest {
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ModifyUserRequest{
        private String name;
        private int imgNo;
    }
}
