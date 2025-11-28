package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.dtos.CrearRutaRequest;
import com.nettalco.backendappservicios.dtos.RutaDetalleCompletoResponse;
import com.nettalco.backendappservicios.dtos.RutaResponse;

import java.util.List;
import java.util.Optional;

public interface IRutaService {
    
    RutaResponse crearRuta(CrearRutaRequest request);
    
    Optional<RutaResponse> obtenerRutaPorId(Integer id);
    
    List<RutaResponse> listarRutas();
    
    void eliminarRuta(Integer id);
    
    RutaResponse actualizarRuta(Integer id, CrearRutaRequest request);
    
    /**
     * Obtiene el detalle completo de una ruta incluyendo viajes activos y horarios
     * Optimizado para reducir llamadas desde el cliente
     */
    Optional<RutaDetalleCompletoResponse> obtenerRutaDetalleCompleto(Integer idRuta);
}

