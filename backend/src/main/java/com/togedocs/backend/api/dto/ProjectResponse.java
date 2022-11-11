package com.togedocs.backend.api.dto;

import lombok.*;

public class ProjectResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Id{
        private Long id;
    }

}
