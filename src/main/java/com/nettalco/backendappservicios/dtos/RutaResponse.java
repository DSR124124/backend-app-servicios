package com.nettalco.backendappservicios.dtos;

import java.math.BigDecimal;
import java.util.List;

public record RutaResponse(
    Integer idRuta,
    String nombre,
    String descripcion,
    String colorMapa,
    Boolean estado,
    List<PuntoRutaResponse> puntos
) {
    public record PuntoRutaResponse(
        Integer idPunto,
        Integer orden,
        BigDecimal latitud,
        BigDecimal longitud,
        String nombreParadero,
        Boolean esParaderoOficial
    ) {}
}

