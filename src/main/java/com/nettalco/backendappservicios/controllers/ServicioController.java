package com.nettalco.backendappservicios.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de servicios
 * Endpoints públicos - no requieren autenticación
 */
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {
    
    /**
     * Listar todos los servicios
     * GET /api/servicios
     */
    @GetMapping
    public ResponseEntity<?> listarServicios() {
        // Por ahora retornamos una lista vacía
        // TODO: Implementar lógica para obtener servicios de la base de datos
        List<Map<String, Object>> servicios = new ArrayList<>();
        
        return ResponseEntity.ok(servicios);
    }
    
    /**
     * Obtener un servicio por ID
     * GET /api/servicios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerServicioPorId(@PathVariable Integer id) {
        // Por ahora retornamos un servicio vacío
        // TODO: Implementar lógica para obtener servicio de la base de datos
        Map<String, Object> servicio = new HashMap<>();
        servicio.put("idServicio", id);
        servicio.put("mensaje", "Servicio no encontrado");
        
        return ResponseEntity.ok(servicio);
    }
    
    /**
     * Crear un nuevo servicio
     * POST /api/servicios
     */
    @PostMapping
    public ResponseEntity<?> crearServicio(@RequestBody Map<String, Object> servicio) {
        // Por ahora retornamos el servicio creado
        // TODO: Implementar lógica para guardar en la base de datos
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Servicio creado exitosamente");
        response.put("servicio", servicio);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Actualizar un servicio
     * PUT /api/servicios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> servicio) {
        // Por ahora retornamos el servicio actualizado
        // TODO: Implementar lógica para actualizar en la base de datos
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Servicio actualizado exitosamente");
        response.put("idServicio", id);
        response.put("servicio", servicio);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Eliminar un servicio
     * DELETE /api/servicios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Integer id) {
        // Por ahora retornamos éxito
        // TODO: Implementar lógica para eliminar de la base de datos
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Servicio eliminado exitosamente");
        response.put("idServicio", id);
        
        return ResponseEntity.ok(response);
    }
}

