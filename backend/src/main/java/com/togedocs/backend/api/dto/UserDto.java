package com.togedocs.backend.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.togedocs.backend.domain.entity.ProjectUserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private int imgNo;
    private ProjectUserRole role;

    @QueryProjection
    public UserDto(Long id, String name,int imgNo,  ProjectUserRole role){
        this.id = id;
        this.name = name;
        this.imgNo = imgNo;
        this.role = role;
    }
}
