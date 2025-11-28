package com.nettalco.backendappservicios.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CrearRutaRequest(
    @NotBlank(message = "El nombre de la ruta es obligatorio")
    String nombre,
    
    String descripcion,
    
    @NotNull(message = "El ID del vehículo es obligatorio")
    Integer idVehiculoDefault,
    
    String colorMapa,
    
    @NotEmpty(message = "La ruta debe tener al menos un punto")
    @Valid
    List<PuntoRutaDto> puntos
) {}

