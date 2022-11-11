package com.togedocs.backend.api.dto;

import lombok.*;

public class ProjectRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateProjectRequest{
        private String title;
        private String desc;
        private Integer imgNo;
    }


}
