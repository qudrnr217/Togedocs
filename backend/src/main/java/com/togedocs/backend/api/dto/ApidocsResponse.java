package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.Apidocs;
import com.togedocs.backend.domain.entity.ColDto;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public class ApidocsResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Ids {
        private Long projectId;
        private String rowId;
        private String colId;

        public static ApidocsResponse.Ids build(Long projectId, String rowId, String colId) {
            return Ids.builder()
                    .projectId(projectId)
                    .rowId(rowId)
                    .colId(colId)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Apidocs {
        private ObjectId id;
        private Long projectId;
        private List<String> rows;
        private List<ColDto> cols;
        private Map<String, Map<String, String>> data;

        public static ApidocsResponse.Apidocs build(com.togedocs.backend.domain.entity.Apidocs apidocs) {
            return Apidocs.builder()
                    .id(apidocs.getId())
                    .projectId(apidocs.getProjectId())
                    .rows(apidocs.getRows())
                    .cols(apidocs.getCols())
                    .data(apidocs.getData())
                    .build();
        }
    }
}
