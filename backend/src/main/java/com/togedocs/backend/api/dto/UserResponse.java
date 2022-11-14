package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Id {
        private int id;
    }

    @Data
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Info {
        //        private String title;
//        private String desc;
        private List<User> users;
        private List<Long> projectId;

        public static UserResponse.Info build(List<User> users, List<Long> projectId) {
            return Info.builder()
                    .users(users)
                    .projectId(projectId)
                    .build();
        }
    }

}
