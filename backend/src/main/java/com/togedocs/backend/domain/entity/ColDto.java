package com.togedocs.backend.domain.entity;

import lombok.*;

import java.util.UUID;

// TODO 어노테이션 수정해야함.
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ColDto {
    private String uuid;
    private String name;
    private String type;
    private int width;
    private ColCategory category;

    public static ColDto build(String uuid, String name, String type, int width, ColCategory category){
        return ColDto.builder()
                .uuid(uuid)
                .name(name)
                .type(type)
                .width(width)
                .category(category)
                .build();
    }
}
