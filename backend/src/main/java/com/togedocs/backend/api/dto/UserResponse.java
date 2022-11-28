package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.ProjectUserRole;
import com.togedocs.backend.domain.entity.User;
import lombok.*;

import java.util.List;

public class UserResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Id {
        private int id;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class accessToken {
        private Token token;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfo {
        private String name;
        private int imgNo;

        public static UserResponse.UserInfo build(User user) {
            return UserInfo.builder()
                    .name(user.getName())
                    .imgNo(user.getImgNo())
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProjectInfo {
        private Long projectId;
        private int imgNo;
        private ProjectUserRole role;
        @Setter
        private String title;
        @Setter
        private String desc;
        private List<String> names;
        public static UserResponse.ProjectInfo build(Long projectId, int imgNo, ProjectUserRole role, List<String> names){
            return ProjectInfo.builder()
                    .projectId(projectId)
                    .imgNo(imgNo)
                    .role(role)
                    .names(names)
                    .build();
        }

        public static UserResponse.ProjectInfo build(Long projectId, int imgNo, ProjectUserRole role, String title, String desc, List<String> names) {
            return ProjectInfo.builder()
                    .projectId(projectId)
                    .imgNo(imgNo)
                    .role(role)
                    .title(title)
                    .desc(desc)
                    .names(names)
                    .build();
        }
    }

}
