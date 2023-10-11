package com.covinoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;



import com.covinoc.configs.JwtTokenProvider;
import com.covinoc.models.JwtResponse;
import com.covinoc.models.LoginRequest;
import com.covinoc.utils.AuthenticationException;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
        try {
            // Autenticar al usuario
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Generar un token JWT
            String token = jwtTokenProvider.generateToken(loginRequest);

            return new JwtResponse(token);
        } catch (Exception e) {
            throw new AuthenticationException("Credenciales inválidas");
        }
    }
}

