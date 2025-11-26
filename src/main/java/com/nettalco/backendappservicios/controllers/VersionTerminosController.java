package com.nettalco.backendappservicios.controllers;

import com.nettalco.backendappservicios.dtos.VersionTerminosResponseDTO;
import com.nettalco.backendappservicios.servicesinterfaces.IVersionTerminosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/versiones-terminos")
@CrossOrigin(origins = "*")
public class VersionTerminosController {

    @Autowired
    private IVersionTerminosService contR;


    @GetMapping("/actual")
    public ResponseEntity<VersionTerminosResponseDTO> getVersionActual() {
        List<Object[]> rawData = contR.findFirstByOrderByFechaVigenciaInicioDesc();
        
        if (rawData == null || rawData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        // Tomamos el primer elemento ya que la consulta devuelve solo uno (LIMIT 1)
        Object[] data = rawData.get(0);
        VersionTerminosResponseDTO dto = new VersionTerminosResponseDTO();

        // Parsing and setting values according to DTO fields
        dto.setIdVersion(convertirAInteger(data[0]));
        dto.setNumeroVersion(convertirAString(data[1]));
        dto.setTitulo(convertirAString(data[2]));
        dto.setContenido(convertirAString(data[3]));
        dto.setResumenCambios(convertirAString(data[4]));
        dto.setFechaCreacion(convertirALocalDateTime(data[5]));
        dto.setFechaVigenciaInicio(convertirALocalDateTime(data[6]));
        dto.setFechaVigenciaFin(convertirALocalDateTime(data[7]));
        dto.setEsVersionActual(convertirABoolean(data[8]));
        dto.setEstado(convertirAString(data[9]));
        dto.setIdUsuarioCreador(convertirAInteger(data[10]));
        dto.setNombreUsuarioCreador(convertirAString(data[11]));
        dto.setFechaModificacion(convertirALocalDateTime(data[12]));

        return ResponseEntity.ok(dto);
    }

    // Métodos auxiliares para conversión de tipos
    private Integer convertirAInteger(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        return null;
    }

    private String convertirAString(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    private Boolean convertirABoolean(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() != 0;
        }
        return null;
    }

    private LocalDateTime convertirALocalDateTime(Object obj) {
        if (obj == null) return null;
        if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        }
        if (obj instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) obj).toLocalDateTime();
        }
        if (obj instanceof java.sql.Date) {
            return ((java.sql.Date) obj).toLocalDate().atStartOfDay();
        }
        return null;
    }

}

