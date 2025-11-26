package com.nettalco.backendappservicios.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador simple para health check
 */
@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = "*")
public class HealthController {
    
    /**
     * Health check público - acepta GET y HEAD
     * Ruta: /servicios/api/health
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "service", "backend-servicios",
            "timestamp", System.currentTimeMillis()
        ));
    }
}

