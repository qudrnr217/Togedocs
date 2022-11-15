package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.LogDto;
import lombok.*;

import java.util.List;

public class ApilogsResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Logs {
        private List<LogDto> logs;

        public static ApilogsResponse.Logs build(List<LogDto> logDtos) {
            return ApilogsResponse.Logs.builder()
                    .logs(logDtos).build();
        }
    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LogIdsAndTime {
        private Long projectId;
        private String rowId;
        private String logTime;

        public static ApilogsResponse.LogIdsAndTime build(Long projectId, String rowId, String logTime) {
            return LogIdsAndTime.builder()
                    .projectId(projectId)
                    .rowId(rowId)
                    .logTime(logTime)
                    .build();
        }
    }

}
