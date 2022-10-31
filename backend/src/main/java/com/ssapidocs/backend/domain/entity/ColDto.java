package com.ssapidocs.backend.domain.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class ColDto {
    private String uuid;
    private String name;
    private String type;
}
