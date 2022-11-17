package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.UserRequest;
import com.togedocs.backend.api.dto.UserResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
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

    @Transactional
    @PatchMapping("/info")
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


    @GetMapping("/project")
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
}
