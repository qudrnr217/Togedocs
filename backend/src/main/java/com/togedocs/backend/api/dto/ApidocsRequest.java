package com.togedocs.backend.api.dto;

import lombok.*;

public class ApidocsRequest {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AddColRequest {
        private String name;
        private String type;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MoveItemRequest {
        private String fromId;
        private int toIndex;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateCellRequest {
        private String rowId;
        private String colId;
        private String content; // col type이 추가되면 타입 변경 가능성 있음.
    }

}
