package com.togedocs.backend.api.dto;

import com.togedocs.backend.domain.entity.Project;
import com.togedocs.backend.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserProjectDto {

//    private int id;
    User user;
    Project project;
    private String role;
}
