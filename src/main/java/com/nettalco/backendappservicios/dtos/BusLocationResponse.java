package com.nettalco.backendappservicios.dtos;

import java.time.OffsetDateTime;

public record BusLocationResponse(
    Double latitude,
    Double longitude,
    Double heading,
    Double speed,
    OffsetDateTime timestamp
) {}
