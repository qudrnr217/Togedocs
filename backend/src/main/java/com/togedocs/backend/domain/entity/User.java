package com.togedocs.backend.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data //getter setter를 대신
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int imgNo;
    private String email;

    private String name;
    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;

    //생성자 단축키 alt+insert

    @Builder
    public User(int imgNo, String email, String name,String provider, String providerId, Timestamp createDate) {
        this.imgNo = imgNo;
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}