package com.togedocs.backend.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.togedocs.backend.domain.entity.ProjectUserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class UserDto {
    private String uuid;
    private String name;
    private int imgNo;
    private ProjectUserRole role;

    @QueryProjection
    public UserDto(String uuid, String name, int imgNo, ProjectUserRole role) {
        this.uuid = uuid;
        this.name = name;
        this.imgNo = imgNo;
        this.role = role;
    }
}
