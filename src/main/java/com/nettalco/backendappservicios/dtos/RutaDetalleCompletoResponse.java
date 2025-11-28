package com.nettalco.backendappservicios.dtos;

import java.util.List;

/**
 * DTO optimizado que combina ruta, viajes activos y horarios en una sola respuesta
 * para reducir llamadas desde el cliente y la carga del APK
 */
public record RutaDetalleCompletoResponse(
    RutaResponse ruta,
    List<ViajeActivoResponse> viajesActivos,
    List<HorarioResponse> horarios
) {
    public record HorarioResponse(
        String horaInicio,
        String horaFin,
        String frecuencia,
        String diasSemana
    ) {}
}
