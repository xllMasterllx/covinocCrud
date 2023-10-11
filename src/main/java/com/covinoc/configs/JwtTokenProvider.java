package com.covinoc.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.covinoc.models.LoginRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

//generar, validar y gestionar tokens JWT
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private SecretKey secretKey;
	
	@Value("${jwt.expiration}")
    private long validityInMilliseconds;
	
	public JwtTokenProvider() {
        this.secretKey = generateSecretKeyForHS512();
    }

	/**
	 * Genera un token JWT basado en la información proporcionada, 
	 * incluyendo el nombre de usuario del usuario autenticado.
	 */
    public String generateToken(LoginRequest loginRequest) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", loginRequest.getUsername());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + validityInMilliseconds);
        System.out.println("2");
        return Jwts.builder()
                .setSubject(loginRequest.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }
    
    /**
     * Genera una clave secreta segura para el algoritmo de firma HS512.
     */
    private static SecretKey generateSecretKeyForHS512() {
    	  return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    	}

    /**
     * Valida y verifica la autenticidad de un token JWT para garantizar su integridad y vigencia.
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
        	System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    /**
     * Obtiene el nombre de usuario contenido en un token JWT.
     */
    public String getUsernameFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
    
}
