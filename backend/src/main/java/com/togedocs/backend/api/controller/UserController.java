package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/getToken")
    public String getToken(){

        String accessToken = "";
        String refreshToken = "";
//        token.split("token=")
        return "<h1>Test</h1>";
    }

    @PatchMapping("/info")
    public ResponseEntity<?> modifyUser(@RequestBody UserRequest.ModifyUserRequest userRequest, Principal principal ){
        UserResponse.Id response;

        String providerId = principal.getName();
        System.out.println(providerId);

        response = userService.modifyUser(userRequest,providerId);




        return ResponseEntity.status(200).body(response);
    }

}
