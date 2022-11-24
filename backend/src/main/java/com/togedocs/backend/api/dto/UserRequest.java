package com.togedocs.backend.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

public class UserRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ModifyUserRequest{
        @NotEmpty
        private String name;
        @NotEmpty
        private int imgNo;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfoRequest{
        // TODO : FE에서 Req를 바꿔야함.
        private String email;
        private String providerId;
        private int imgNo;
        private String name;
    }
}
