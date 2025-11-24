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
        response.put("username", userDetails.getUsername());
        response.put("rol", userDetails.getNombreRol());
        response.put("mensaje", "Token válido - Usuario autenticado");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Listar todos los clientes
     * GET /api/clientes
     */
    @GetMapping
    public ResponseEntity<?> listarClientes() {
        UserDetails userDetails = getUserDetails();
        
        // Aquí va tu lógica de negocio
        // Puedes filtrar por usuario: userDetails.getIdUsuario()
        // Puedes validar rol: userDetails.getNombreRol()
        
        // Ejemplo de datos simulados
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Lista de clientes");
        response.put("solicitadoPor", userDetails.getUsername());
        response.put("rol", userDetails.getNombreRol());
        response.put("datos", new Object[]{
            Map.of("id", 1, "nombre", "Cliente 1", "email", "cliente1@ejemplo.com"),
            Map.of("id", 2, "nombre", "Cliente 2", "email", "cliente2@ejemplo.com"),
            Map.of("id", 3, "nombre", "Cliente 3", "email", "cliente3@ejemplo.com")
        });
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtener un cliente por ID
     * GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
        UserDetails userDetails = getUserDetails();
        
        // Aquí va tu lógica para buscar el cliente
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("id", id);
        cliente.put("nombre", "Cliente " + id);
        cliente.put("email", "cliente" + id + "@ejemplo.com");
        cliente.put("consultadoPor", userDetails.getUsername());
        
        return ResponseEntity.ok(cliente);
    }
    
    /**
     * Crear un nuevo cliente (solo administradores)
     * POST /api/clientes
     */
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Map<String, Object> cliente) {
        UserDetails userDetails = getUserDetails();
        
        // Validar rol - solo administradores pueden crear clientes
        if (!"Administrador".equals(userDetails.getNombreRol())) {
            return ResponseEntity.status(403)
                .body(Map.of("error", "No tienes permisos para crear clientes"));
        }
        
        // Aquí va tu lógica de creación
        cliente.put("creadoPor", userDetails.getIdUsuario());
        cliente.put("id", System.currentTimeMillis()); // ID simulado
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Cliente creado exitosamente",
            "cliente", cliente
        ));
    }
    
    /**
     * Actualizar un cliente
     * PUT /api/clientes/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(
            @PathVariable Long id,
            @RequestBody Map<String, Object> cliente) {
        UserDetails userDetails = getUserDetails();
        
        // Validar permisos según necesites
        if (!"Administrador".equals(userDetails.getNombreRol()) && 
            !"Gestor".equals(userDetails.getNombreRol())) {
            return ResponseEntity.status(403)
                .body(Map.of("error", "No tienes permisos para actualizar clientes"));
        }
        
        // Aquí va tu lógica de actualización
        cliente.put("id", id);
        cliente.put("actualizadoPor", userDetails.getIdUsuario());
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Cliente actualizado exitosamente",
            "cliente", cliente
        ));
    }
    
    /**
     * Eliminar un cliente (solo administradores)
     * DELETE /api/clientes/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        UserDetails userDetails = getUserDetails();
        
        // Validar rol - solo administradores pueden eliminar
        if (!"Administrador".equals(userDetails.getNombreRol())) {
            return ResponseEntity.status(403)
                .body(Map.of("error", "No tienes permisos para eliminar clientes"));
        }
        
        // Aquí va tu lógica de eliminación
        
        return ResponseEntity.ok(Map.of(
            "mensaje", "Cliente eliminado exitosamente",
            "id", id,
            "eliminadoPor", userDetails.getUsername()
        ));
    }
}

