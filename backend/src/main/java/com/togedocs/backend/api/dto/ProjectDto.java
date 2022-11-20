package com.togedocs.backend.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
public class ProjectDto {


    private int imgNo;

    private String code; //초대 코드


}
