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
        private String myname;
        private String role;
        private long projectId;
        private String title;
        private String desc;
        private List<String> names ;
        private int imgNo;

        public static UserResponse.Info build(String myname, String role, Long projectId,String title, String desc,List<String> names, int imgNo) {
            return Info.builder()
                    .myname(myname)
                    .role(role)
                    .projectId(projectId)
                    .title(title)
                    .desc(desc)
                    .imgNo(imgNo)
                    .names(names)
                    .build();
        }
    }

}
