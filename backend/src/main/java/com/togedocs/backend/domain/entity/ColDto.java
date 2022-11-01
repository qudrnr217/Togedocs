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
}
