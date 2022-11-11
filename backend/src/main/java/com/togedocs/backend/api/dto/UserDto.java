package com.togedocs.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
//    private int id;

    private String username;

    private String password;

    private String email;

    // TODO: 나중에 지울 컬럼
    private String role;


    //소셜로그인을 할 경우 어디서 받아온지 알 수 있게 알려주는 String
    private String provider;
    private String providerId;
//    private Timestamp createDate;
}
