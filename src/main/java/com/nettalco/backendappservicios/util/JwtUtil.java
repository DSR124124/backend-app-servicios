package com.nettalco.backendappservicios.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    /**
     * Obtiene la clave de firma para validar el token JWT
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * Extrae todos los claims del token JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * Extrae el username (subject) del token
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    /**
     * Extrae el ID del usuario del token
     */
    public Integer extractIdUsuario(String token) {
        return extractAllClaims(token).get("idUsuario", Integer.class);
    }
    
    /**
     * Extrae el ID del rol del token
     */
    public Integer extractIdRol(String token) {
        return extractAllClaims(token).get("idRol", Integer.class);
    }
    
    /**
     * Extrae el nombre del rol del token
     */
    public String extractNombreRol(String token) {
        return extractAllClaims(token).get("nombreRol", String.class);
    }
    
    /**
     * Valida si el token es válido (no expirado)
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Extrae la fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}

