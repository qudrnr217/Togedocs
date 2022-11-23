package com.togedocs.backend.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ApidocsRequest {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class AddColRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String type;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MoveItemRequest {
        @NotEmpty
        private String fromId;
        @PositiveOrZero
        private Integer toIndex;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateCellRequest {
        @NotEmpty
        private String rowId;
        @NotEmpty
        private String colId;
        private String content; // col type이 추가되면 타입 변경 가능성 있음.
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateProjectInfoRequest {
        @NotEmpty
        private String title;
        private String desc;
        private String baseUrl;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UpdateColRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String type;
        @Positive
        private Integer width;
    }
}
