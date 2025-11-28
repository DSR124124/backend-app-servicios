package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.dtos.BusLocationResponse;
import com.nettalco.backendappservicios.dtos.TripDetailResponse;
import com.nettalco.backendappservicios.dtos.ViajeActivoResponse;

import java.util.List;
import java.util.Optional;

public interface IViajeService {
    
    /**
     * Obtiene los detalles de un viaje incluyendo la ruta, información del bus y conductor
     */
    Optional<TripDetailResponse> obtenerDetalleViaje(Integer idViaje);
    
    /**
     * Obtiene la ubicación actual del bus para un viaje
     */
    Optional<BusLocationResponse> obtenerUbicacionActual(Integer idViaje);
    
    /**
     * Obtiene los viajes activos (en_curso o programados) para una ruta específica
     */
    List<ViajeActivoResponse> obtenerViajesActivosPorRuta(Integer idRuta);
}
