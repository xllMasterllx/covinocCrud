package com.covinoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.covinoc.models.JwtResponse;
import com.covinoc.models.LoginRequest;
import com.covinoc.services.AuthService;
import com.covinoc.utils.AuthenticationException;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/auth")
@ApiOperation(value = "auth")
public class AuthController {
	
	private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse response = authService.authenticateAndGenerateToken(loginRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

