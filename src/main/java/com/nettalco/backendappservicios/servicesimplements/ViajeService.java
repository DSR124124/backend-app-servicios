package com.nettalco.backendappservicios.servicesimplements;

import com.nettalco.backendappservicios.dtos.BusLocationResponse;
import com.nettalco.backendappservicios.dtos.TripDetailResponse.RoutePointResponse;
import com.nettalco.backendappservicios.dtos.TripDetailResponse;
import com.nettalco.backendappservicios.dtos.ViajeActivoResponse;
import com.nettalco.backendappservicios.entities.RutaPunto;
import com.nettalco.backendappservicios.entities.UbicacionTiempoReal;
import com.nettalco.backendappservicios.entities.Viaje;
import com.nettalco.backendappservicios.repositories.UbicacionTiempoRealRepository;
import com.nettalco.backendappservicios.repositories.ViajeRepository;
import com.nettalco.backendappservicios.servicesinterfaces.IViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ViajeService implements IViajeService {
    
    @Autowired
    private ViajeRepository viajeRepository;
    
    @Autowired
    private UbicacionTiempoRealRepository ubicacionRepository;
    
    @Override
    @Transactional(readOnly = true)
    public Optional<TripDetailResponse> obtenerDetalleViaje(Integer idViaje) {
        return viajeRepository.findByIdWithRelations(idViaje)
            .map(this::convertirATripDetailResponse);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<BusLocationResponse> obtenerUbicacionActual(Integer idViaje) {
        // Verificar que el viaje existe
        if (!viajeRepository.existsById(idViaje)) {
            return Optional.empty();
        }
        
        // Obtener la última ubicación registrada para este viaje
        List<UbicacionTiempoReal> ubicaciones = ubicacionRepository
            .findByViajeIdOrderByFechaRegistroDesc(idViaje);
        
        if (ubicaciones.isEmpty()) {
            return Optional.empty();
        }
        
        UbicacionTiempoReal ultimaUbicacion = ubicaciones.get(0);
        
        BusLocationResponse response = new BusLocationResponse(
            ultimaUbicacion.getLatitud().doubleValue(),
            ultimaUbicacion.getLongitud().doubleValue(),
            ultimaUbicacion.getRumbo() != null ? ultimaUbicacion.getRumbo().doubleValue() : null,
            ultimaUbicacion.getVelocidadKmh() != null ? ultimaUbicacion.getVelocidadKmh().doubleValue() : null,
            ultimaUbicacion.getFechaRegistro()
        );
        
        return Optional.of(response);
    }
    
    private TripDetailResponse convertirATripDetailResponse(Viaje viaje) {
        // Obtener información del bus
        String busPlate = viaje.getBus() != null ? viaje.getBus().getPlaca() : "";
        String busModel = viaje.getBus() != null ? viaje.getBus().getModelo() : null;
        
        // Información del conductor (por ahora solo el ID, ya que está en otra BD)
        // En producción, deberías hacer una llamada al otro microservicio o tener una vista
        String driverName = "Conductor " + viaje.getIdConductor(); // Placeholder
        String driverPhoto = null; // Placeholder
        
        // Convertir puntos de la ruta
        List<RoutePointResponse> routePoints = viaje.getRuta().getPuntos().stream()
            .map(this::convertirARoutePointResponse)
            .collect(Collectors.toList());
        
        // Estado del viaje
        String status = viaje.getEstado() != null ? viaje.getEstado() : "desconocido";
        
        // Tiempo estimado de llegada (por ahora null, se puede calcular basado en la ubicación actual)
        Integer estimatedArrivalMinutes = null;
        
        return new TripDetailResponse(
            viaje.getIdViaje(),
            busPlate,
            busModel,
            driverName,
            driverPhoto,
            routePoints,
            status,
            estimatedArrivalMinutes
        );
    }
    
    private RoutePointResponse convertirARoutePointResponse(RutaPunto punto) {
        return new RoutePointResponse(
            punto.getLatitud().doubleValue(),
            punto.getLongitud().doubleValue(),
            punto.getNombreParadero(),
            punto.getOrden()
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ViajeActivoResponse> obtenerViajesActivosPorRuta(Integer idRuta) {
        List<Viaje> viajes = viajeRepository.findByRutaIdAndActivos(idRuta);
        
        return viajes.stream()
            .map(this::convertirAViajeActivoResponse)
            .collect(Collectors.toList());
    }
    
    private ViajeActivoResponse convertirAViajeActivoResponse(Viaje viaje) {
        String busPlate = viaje.getBus() != null ? viaje.getBus().getPlaca() : "";
        String busModel = viaje.getBus() != null ? viaje.getBus().getModelo() : null;
        String driverName = "Conductor " + viaje.getIdConductor(); // Placeholder
        
        return new ViajeActivoResponse(
            viaje.getIdViaje(),
            viaje.getRuta().getIdRuta(),
            busPlate,
            busModel,
            driverName,
            viaje.getEstado() != null ? viaje.getEstado() : "desconocido",
            viaje.getFechaInicioProgramada(),
            viaje.getFechaFinProgramada()
        );
    }
}
