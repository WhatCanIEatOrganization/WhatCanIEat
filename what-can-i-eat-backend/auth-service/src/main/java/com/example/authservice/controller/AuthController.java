package com.example.authservice.controller;

import com.example.authservice.dto.AuthRequestDto;
import com.example.authservice.entity.UserEntity;
import com.example.authservice.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/auth")
public class AuthController {


    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserEntity user) {
        return service.saveUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        return service.login(authRequestDto, response);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}