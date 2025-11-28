package com.nettalco.backendappservicios.dtos;

import java.util.List;

public record TripDetailResponse(
    Integer tripId,
    String busPlate,
    String busModel,
    String driverName,
    String driverPhoto,
    List<RoutePointResponse> routePoints,
    String status,
    Integer estimatedArrivalMinutes
) {
    public record RoutePointResponse(
        Double latitude,
        Double longitude,
        String name,
        Integer order
    ) {}
}
