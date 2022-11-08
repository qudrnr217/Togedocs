package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/getToken")
    public String getToken(@RequestParam("token")String token){
        System.out.println("hi");
        System.out.println(token);
        String accessToken = "";
        String refreshToken = "";
//        token.split("token=")
        return "Test";
    }

}
