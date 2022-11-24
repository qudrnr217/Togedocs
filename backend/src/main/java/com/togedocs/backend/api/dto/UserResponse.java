package com.togedocs.backend.api.dto;

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
        private String userName;
        private int imgNo;

        public static UserResponse.UserInfo build(User user) {
            return UserInfo.builder()
                    .userName(user.getName())
                    .imgNo(user.getImgNo())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ProjectInfo {
        private Long projectId;
        private String role;
        private String title;
        private String desc;
        private List<String> names;
        private int imgNo;

        public static UserResponse.ProjectInfo build(Long projectId, String role, String title, String desc, List<String> names, int imgNo) {
            return ProjectInfo.builder()
                    .projectId(projectId)
                    .role(role)
                    .title(title)
                    .desc(desc)
                    .imgNo(imgNo)
                    .names(names)
                    .build();
        }
    }

}
