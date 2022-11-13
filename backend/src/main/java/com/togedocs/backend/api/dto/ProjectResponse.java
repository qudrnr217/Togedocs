package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.ProjectUserRole;
import lombok.*;

import java.util.List;

public class ProjectResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Id{
        private Long projectId;
        public static ProjectResponse.Id build(Long projectId){
            return Id.builder()
                    .projectId(projectId)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MemberManageInfo{
        private Long projectId;
        private List<UserDto> members;
        private String code;
        public static ProjectResponse.MemberManageInfo build(Long projectId, List<UserDto> members, String code){
            return MemberManageInfo.builder()
                    .projectId(projectId)
                    .members(members)
                    .code(code)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProjectUser{
        private Long projectId;
        private Long userId;
        private ProjectUserRole role;
        public static ProjectResponse.ProjectUser build(Long projectId, Long userId, ProjectUserRole role) {
            return ProjectUser.builder()
                    .projectId(projectId)
                    .userId(userId)
                    .role(role)
                    .build();
        }
    }
}
