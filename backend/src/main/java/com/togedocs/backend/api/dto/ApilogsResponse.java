package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.Apilogs;
import com.togedocs.backend.domain.entity.LogDto;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

public class ApilogsResponse {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class LogsOfOneApi {
        private List<LogDto> logs;

        public static ApilogsResponse.LogsOfOneApi build(List<LogDto> logDtos) {
            return ApilogsResponse.LogsOfOneApi.builder()
                    .logs(logDtos).build();
        }

    }


    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class NewLogDto {
        private Long projectId;
        private String rowId;
        private String logTime;

        public static ApilogsResponse.NewLogDto build(Long projectId, String rowId, String logTime) {
            return NewLogDto.builder()
                    .projectId(projectId)
                    .rowId(rowId)
                    .logTime(logTime)
                    .build();
        }
    }

}
