package com.nettalco.backendappservicios.controllers;

import com.nettalco.backendappservicios.dtos.GPSIngestaRequest;
import com.nettalco.backendappservicios.dtos.GPSIngestaResponse;
import com.nettalco.backendappservicios.servicesinterfaces.IUbicacionTiempoRealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller optimizado para ingesta de GPS en tiempo real.
 * Diseñado para alto throughput con procesamiento asíncrono.
 */
@RestController
@RequestMapping("/api/gps")
@CrossOrigin(origins = "*")
public class GPSController {
    
    @Autowired
    private IUbicacionTiempoRealService ubicacionService;
    
    /**
     * Endpoint síncrono para registro de ubicación GPS.
     * Usa transacciones REQUIRES_NEW para minimizar bloqueos.
     */
    @PostMapping("/ubicacion")
    public ResponseEntity<?> registrarUbicacion(@Valid @RequestBody GPSIngestaRequest request) {
        try {
            GPSIngestaResponse response = ubicacionService.registrarUbicacion(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al registrar ubicación: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint asíncrono para registro de ubicación GPS.
     * Retorna inmediatamente mientras procesa en background.
     * Ideal para alto throughput.
     */
    @PostMapping("/ubicacion/async")
    public ResponseEntity<?> registrarUbicacionAsync(@Valid @RequestBody GPSIngestaRequest request) {
        try {
            ubicacionService.registrarUbicacionAsync(request)
                .thenAccept(response -> {
                    // Log opcional: la ubicación fue registrada exitosamente
                    // En producción, podrías enviar esto a un sistema de notificaciones
                })
                .exceptionally(ex -> {
                    // Log del error
                    return null;
                });
            
            return ResponseEntity.accepted()
                .body(Map.of(
                    "mensaje", "Ubicación en proceso de registro",
                    "idViaje", request.idViaje()
                ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al iniciar registro de ubicación: " + e.getMessage()));
        }
    }
}

