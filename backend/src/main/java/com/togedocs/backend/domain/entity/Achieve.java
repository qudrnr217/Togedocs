package com.togedocs.backend.domain.entity;

import lombok.*;

import javax.persistence.*;

// TODO 어노테이션 수정해야함.
@NoArgsConstructor
@Data
@Entity
@Getter
@Table(name = "tb_achievement")
public class Achieve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achieveSeq = null;

    @Column(name = "achieve_category")
    private String achieveCategory;

    @Column(name = "achieve_subcategory")
    private String achieveSubcategory;

    @Column(name = "achieve_name")
    private String achieveName;

    @Column(name = "achieve_desc")
    private String achieveDesc;


}

