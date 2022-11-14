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
        private long projectId;
        //        private String title;
//        private String desc;
        private List<String> names ;
        private List<Integer> imgNos;

        public static UserResponse.Info build(Long projectId,List<String> names, List<Integer> imgNos) {
            return Info.builder()
                    .projectId(projectId)
                    .names(names)
                    .imgNos(imgNos)
                    .build();
        }
    }

}
