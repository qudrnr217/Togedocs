package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/getToken")
    public String getToken(){

        String accessToken = "";
        String refreshToken = "";
//        token.split("token=")
        return "<h1>Test</h1>";
    }

//    {email: "",
//    profileImage: "",
//    projects: [
//        {
//            name:""
//        }...]
//    }

}
