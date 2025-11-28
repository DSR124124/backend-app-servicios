package com.nettalco.backendappservicios.dtos;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record GPSIngestaRequest(
    @NotNull(message = "El ID del viaje es obligatorio")
    Integer idViaje,
    
    @NotNull(message = "La latitud es obligatoria")
    BigDecimal latitud,
    
    @NotNull(message = "La longitud es obligatoria")
    BigDecimal longitud,
    
    BigDecimal velocidadKmh,
    
    BigDecimal rumbo
) {}

