package com.example.authservice.service;

import com.example.authservice.dto.AuthRequestDto;
import com.example.authservice.entity.UserEntity;
import com.example.authservice.exception.EmailAlreadyTakenException;
import com.example.authservice.exception.UsernameAlreadyTakenException;
import com.example.authservice.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public String saveUser(UserEntity credential) {
        repository.findByName(credential.getName())
                .ifPresent(s -> {
                    throw new UsernameAlreadyTakenException("Username already taken");
                });
        repository.findByEmail(credential.getEmail())
                .ifPresent(s -> {
                    throw new EmailAlreadyTakenException("Email already taken");
                });
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String login(AuthRequestDto authRequestDto, HttpServletResponse response) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            String jwt = jwtService.generateToken(authRequestDto.getUsername());
            Cookie cookie = new Cookie("jwt", jwt);
            // cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setDomain("52.57.77.107");
            response.addCookie(cookie);
            return "Logged in successfully";
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }


}
