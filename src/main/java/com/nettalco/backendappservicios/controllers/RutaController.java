package com.nettalco.backendappservicios.controllers;

import com.nettalco.backendappservicios.dtos.CrearRutaRequest;
import com.nettalco.backendappservicios.dtos.RutaDetalleCompletoResponse;
import com.nettalco.backendappservicios.dtos.RutaResponse;
import com.nettalco.backendappservicios.servicesinterfaces.IRutaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin(origins = "*")
public class RutaController {
    
    @Autowired
    private IRutaService rutaService;
    
    @PostMapping
    public ResponseEntity<?> crearRuta(@Valid @RequestBody CrearRutaRequest request) {
        try {
            RutaResponse response = rutaService.crearRuta(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al crear la ruta: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerRutaPorId(@PathVariable Integer id) {
        Optional<RutaResponse> ruta = rutaService.obtenerRutaPorId(id);
        if (ruta.isPresent()) {
            return ResponseEntity.ok(ruta.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public ResponseEntity<List<RutaResponse>> listarRutas() {
        List<RutaResponse> rutas = rutaService.listarRutas();
        return ResponseEntity.ok(rutas);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarRuta(
            @PathVariable Integer id,
            @Valid @RequestBody CrearRutaRequest request) {
        try {
            RutaResponse response = rutaService.actualizarRuta(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al actualizar la ruta: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarRuta(@PathVariable Integer id) {
        try {
            rutaService.eliminarRuta(id);
            return ResponseEntity.ok(Map.of("mensaje", "Ruta eliminada exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al eliminar la ruta: " + e.getMessage()));
        }
    }
    
    /**
     * Endpoint optimizado que devuelve ruta completa con viajes activos y horarios
     * Reduce múltiples llamadas desde el cliente
     * GET /api/rutas/{id}/completo
     */
    @GetMapping("/{id}/completo")
    public ResponseEntity<?> obtenerRutaCompleta(@PathVariable Integer id) {
        try {
            Optional<RutaDetalleCompletoResponse> detalle = rutaService.obtenerRutaDetalleCompleto(id);
            
            if (detalle.isPresent()) {
                return ResponseEntity.ok(detalle.get());
            }
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Ruta no encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error al obtener el detalle completo: " + e.getMessage()));
        }
    }
}

