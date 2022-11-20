package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.Token;
import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.UserService;
import com.togedocs.backend.common.security.config.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    @Transactional
    @PatchMapping("/user/info")
    public ResponseEntity<?> modifyUser(@RequestBody UserRequest.ModifyUserRequest userRequest, Principal principal) {
        UserResponse.Id response;
        String providerId = principal.getName();
        try {
            response = userService.modifyUser(userRequest, providerId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/user/info/{userId}")
    public ResponseEntity<?> getUserNameAndImgNo(@PathVariable Long userId) {
        UserResponse.userNameAndImgNo response;
        try {
            response = userService.getUserNameAndImgNo(userId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/user/project")
    public ResponseEntity<?> getProjectInfo(Principal principal) {
        List<UserResponse.Info> response;
        String providerId = principal.getName();
        try {
            response = userService.getUserInfo(providerId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> getAccessToken(@RequestBody UserRequest.UserInfoRequest userRequest){
        Token response;
        try {
            response=tokenService.generateToken(userRequest.getUserId(),userRequest.getName(),userRequest.getImgNo(),userRequest.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Unauthorization Token");
        }
        return ResponseEntity.status(200).body(response.getToken());
    }

}
