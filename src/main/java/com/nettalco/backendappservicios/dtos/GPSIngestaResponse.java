package com.nettalco.backendappservicios.dtos;

import java.time.OffsetDateTime;

public record GPSIngestaResponse(
    Long idTracking,
    Integer idViaje,
    OffsetDateTime fechaRegistro,
    String mensaje
) {}

