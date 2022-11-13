package com.togedocs.backend.api.dto;

import lombok.*;

public class UserResponse {
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Id{
        private int id;
    }
}
