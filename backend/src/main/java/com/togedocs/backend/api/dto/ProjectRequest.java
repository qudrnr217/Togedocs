package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.ProjectUserRole;
import lombok.*;

public class ProjectRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class CreateProjectRequest {
        private String title;
        private String desc;
        private Integer imgNo;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JoinProjectRequest {
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RemoveMemberRequest {
        private Long userId;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateMemberRoleRequest {
        private Long userId;
        private ProjectUserRole role;
    }

}
