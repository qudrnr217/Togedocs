package com.togedocs.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer imgNo;
    @Column(length = 50, nullable = false)
    private String code; //초대 코드

    @Builder
    public Project(int imgNo, String code) {
        this.imgNo = imgNo;
        this.code = code;
    }

}
