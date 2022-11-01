package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColDto;
import lombok.*;

import java.util.List;
import java.util.Map;

public class ApidocsResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private Long projectId;

        public static ApidocsResponse.OnlyId build(Apidocs apidocs){
            return OnlyId.builder()
                    .projectId(apidocs.getProjectId())
                    .build();
        }
    }
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetOne {
        private Long projectId;
        private List<String> rows;
        private List<ColDto> cols;
        private Map<String, Map<String, String>> data;

        public static ApidocsResponse.GetOne build(Apidocs apidocs){
            return GetOne.builder()
                    .projectId(apidocs.getProjectId())
                    .rows(apidocs.getRows())
                    .cols(apidocs.getCols())
                    .data(apidocs.getData())
                    .build();
        }
    }
}
