package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.Token;
import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.api.service.UserService;
import com.togedocs.backend.common.exception.BusinessException;
import com.togedocs.backend.common.exception.ErrorCode;
import com.togedocs.backend.common.security.config.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> updateUserInfo(@RequestBody UserRequest.ModifyUserRequest userRequest, Principal principal) {
        String providerId = principal.getName();
        userService.updateUserInfo(providerId, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 프로필을 수정했습니다!");
    }

    @GetMapping("/user/info/{providerId}")
    public ResponseEntity<UserResponse.UserInfo> getUserInfo(@PathVariable String providerId) {
        UserResponse.UserInfo response = userService.getUserInfo(providerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/project")
    public ResponseEntity<List<UserResponse.ProjectInfo>> getProjectList(Principal principal) {
        String providerId = principal.getName();
        List<UserResponse.ProjectInfo> response = userService.getProjectList(providerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> getAccessToken(@RequestBody UserRequest.UserInfoRequest userRequest) {
        Token response;
        try {
            response = tokenService.generateToken( userRequest.getEmail(), userRequest.getUuid(), userRequest.getName(), userRequest.getImgNo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorCode.LOGIN_FAILURE);
        }
        return ResponseEntity.status(200).body(response.getToken());
    }

}
