package com.nettalco.backendappservicios.controllers;

import com.nettalco.backendappservicios.security.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de ejemplo para demostrar el uso de JWT en el backend de servicios
 * Puedes usar este controlador como plantilla para tus propios endpoints
 */
@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
    
    /**
     * Método auxiliar para obtener los detalles del usuario autenticado
     */
    private UserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getDetails();
    }
    
    /**
     * Health check público
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "service", "backend-servicios",
            "endpoint", "clientes"
        ));
    }
    
    /**
     * Obtener perfil del usuario autenticado
     * GET /api/clientes/mi-perfil
     */
    @GetMapping("/mi-perfil")
    public ResponseEntity<?> miPerfil() {
        UserDetails userDetails = getUserDetails();
        
        Map<String, Object> response = new HashMap<>();
        response.put("idUsuario", userDetails.getIdUsuario());
        response.put("idRol", userDetails.getIdRol());
        response.put("username", userDetails.getUsername());
        response.put("nombreRol", userDetails.getNombreRol());
        
        // Campos que no están en el JWT - el frontend los obtiene del login response
        response.put("email", null);
        response.put("nombreCompleto", null);
        response.put("fechaUltimoAcceso", null);
        
        return ResponseEntity.ok(response);
    }
    
}

