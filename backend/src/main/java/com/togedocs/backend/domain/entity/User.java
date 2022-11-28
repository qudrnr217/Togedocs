package com.togedocs.backend.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int imgNo;
    private String email;
    private String name;
    private String provider;
    private String uuid;
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public User(int imgNo, String email, String name,String provider, String uuid, Timestamp createDate) {
        this.imgNo = imgNo;
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.uuid = uuid;
        this.createDate = createDate;
    }
}