package com.togedocs.backend.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data //getter setter를 대신
@NoArgsConstructor
public class User {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20, nullable = false)
    private String username;
    @Column(length = 30, nullable = false)
    private String password;
    @Column(length = 50, nullable = false)
    private String email;

    // TODO: 나중에 지울 컬럼
    private String role;


    //소셜로그인을 할 경우 어디서 받아온지 알 수 있게 알려주는 String
    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;



    //생성자 단축키 alt+insert
    @Builder
    public User(String username, String password, String email, String role, String provider, String providerId, Timestamp createDate){
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    @OneToMany(mappedBy = "user")
    List<ProjectUser> projectUsers = new ArrayList<>();
}