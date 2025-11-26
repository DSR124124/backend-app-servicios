package com.nettalco.backendappservicios.controllers;

import com.nettalco.backendappservicios.dtos.VersionTerminosRequestDTO;
import com.nettalco.backendappservicios.dtos.VersionTerminosResponseDTO;
import com.nettalco.backendappservicios.entities.VersionTerminos;
import com.nettalco.backendappservicios.security.UserDetails;
import com.nettalco.backendappservicios.servicesinterfaces.IVersionTerminosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/versiones-terminos")
@CrossOrigin(origins = "*")
public class VersionTerminosController {
    
    @Autowired
    private IVersionTerminosService service;
    
    private UserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getDetails();
    }
    
    @GetMapping
    public ResponseEntity<?> listar() {
        List<VersionTerminos> versiones = service.listar();
        List<VersionTerminosResponseDTO> response = versiones.stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/actual")
    public ResponseEntity<?> obtenerVersionActual() {
        Optional<VersionTerminos> version = service.obtenerVersionActual();
        if (version.isPresent()) {
            return ResponseEntity.ok(convertToResponseDTO(version.get()));
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<VersionTerminos> version = service.obtenerPorId(id);
        if (version.isPresent()) {
            return ResponseEntity.ok(convertToResponseDTO(version.get()));
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody VersionTerminosRequestDTO requestDTO) {
        UserDetails userDetails = getUserDetails();
        
        VersionTerminos version = convertToEntity(requestDTO);
        version.setIdUsuarioCreador(userDetails.getIdUsuario());
        version.setNombreUsuarioCreador(userDetails.getUsername());
        
        if (version.getFechaVigenciaInicio() == null) {
            version.setFechaVigenciaInicio(LocalDateTime.now());
        }
        
        VersionTerminos nuevaVersion = service.crear(version);
        return ResponseEntity.ok(convertToResponseDTO(nuevaVersion));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody VersionTerminosRequestDTO requestDTO) {
        Optional<VersionTerminos> versionExistente = service.obtenerPorId(id);
        if (versionExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        VersionTerminos version = convertToEntity(requestDTO);
        VersionTerminos versionActualizada = service.actualizar(id, version);
        if (versionActualizada != null) {
            return ResponseEntity.ok(convertToResponseDTO(versionActualizada));
        }
        return ResponseEntity.badRequest().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<VersionTerminos> version = service.obtenerPorId(id);
        if (version.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Versión eliminada exitosamente"));
    }
    
    private VersionTerminosResponseDTO convertToResponseDTO(VersionTerminos version) {
        VersionTerminosResponseDTO dto = new VersionTerminosResponseDTO();
        dto.setIdVersion(version.getIdVersion());
        dto.setNumeroVersion(version.getNumeroVersion());
        dto.setTitulo(version.getTitulo());
        dto.setContenido(version.getContenido());
        dto.setResumenCambios(version.getResumenCambios());
        dto.setFechaCreacion(version.getFechaCreacion());
        dto.setFechaVigenciaInicio(version.getFechaVigenciaInicio());
        dto.setFechaVigenciaFin(version.getFechaVigenciaFin());
        dto.setEsVersionActual(version.getEsVersionActual());
        dto.setEstado(version.getEstado() != null ? version.getEstado().name() : null);
        dto.setIdUsuarioCreador(version.getIdUsuarioCreador());
        dto.setNombreUsuarioCreador(version.getNombreUsuarioCreador());
        dto.setFechaModificacion(version.getFechaModificacion());
        return dto;
    }
    
    private VersionTerminos convertToEntity(VersionTerminosRequestDTO dto) {
        VersionTerminos version = new VersionTerminos();
        version.setNumeroVersion(dto.getNumeroVersion());
        version.setTitulo(dto.getTitulo());
        version.setContenido(dto.getContenido());
        version.setResumenCambios(dto.getResumenCambios());
        version.setFechaVigenciaInicio(dto.getFechaVigenciaInicio());
        version.setFechaVigenciaFin(dto.getFechaVigenciaFin());
        version.setEsVersionActual(dto.getEsVersionActual());
        if (dto.getEstado() != null) {
            version.setEstado(VersionTerminos.EstadoVersion.valueOf(dto.getEstado()));
        }
        return version;
    }
}

