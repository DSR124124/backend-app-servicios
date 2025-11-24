package com.nettalco.backendappservicios.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para health checks del servicio
 * Este endpoint es público y no requiere autenticación
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    
    /**
     * Health check general del servicio
     * GET /api/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "backend-servicios");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("mensaje", "Backend de Servicios está funcionando correctamente");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Información del servicio
     * GET /api/info
     */
    @GetMapping("/public/info")
    public ResponseEntity<?> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("nombre", "Backend de Servicios");
        response.put("version", "1.0.0");
        response.put("descripcion", "Backend de servicios con validación JWT");
        response.put("arquitectura", "Dual Backend");
        
        return ResponseEntity.ok(response);
    }
}

