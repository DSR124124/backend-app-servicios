package com.nettalco.backendappservicios.dtos;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PuntoRutaDto(
    @NotNull(message = "El orden es obligatorio")
    Integer orden,
    
    @NotNull(message = "La latitud es obligatoria")
    BigDecimal latitud,
    
    @NotNull(message = "La longitud es obligatoria")
    BigDecimal longitud,
    
    String nombreParadero,
    
    Boolean esParaderoOficial
) {}

