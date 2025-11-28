package com.nettalco.backendappservicios.servicesimplements;

import com.nettalco.backendappservicios.dtos.GPSIngestaRequest;
import com.nettalco.backendappservicios.dtos.GPSIngestaResponse;
import com.nettalco.backendappservicios.entities.UbicacionTiempoReal;
import com.nettalco.backendappservicios.entities.Viaje;
import com.nettalco.backendappservicios.repositories.UbicacionTiempoRealRepository;
import com.nettalco.backendappservicios.repositories.ViajeRepository;
import com.nettalco.backendappservicios.servicesinterfaces.IUbicacionTiempoRealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@Service
public class UbicacionTiempoRealService implements IUbicacionTiempoRealService {
    
    @Autowired
    private UbicacionTiempoRealRepository ubicacionRepository;
    
    @Autowired
    private ViajeRepository viajeRepository;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public GPSIngestaResponse registrarUbicacion(GPSIngestaRequest request) {
        // Validar que el viaje existe y está activo
        Viaje viaje = viajeRepository.findByIdAndActivo(request.idViaje())
            .orElseThrow(() -> new IllegalArgumentException(
                "El viaje con ID " + request.idViaje() + " no existe o no está en curso"));
        
        // Crear la entidad de ubicación
        UbicacionTiempoReal ubicacion = new UbicacionTiempoReal();
        ubicacion.setViaje(viaje);
        ubicacion.setLatitud(request.latitud());
        ubicacion.setLongitud(request.longitud());
        ubicacion.setVelocidadKmh(request.velocidadKmh());
        ubicacion.setRumbo(request.rumbo());
        ubicacion.setFechaRegistro(OffsetDateTime.now());
        
        // Guardar usando REQUIRES_NEW para minimizar el tiempo de bloqueo de transacción
        UbicacionTiempoReal ubicacionGuardada = ubicacionRepository.save(ubicacion);
        
        return new GPSIngestaResponse(
            ubicacionGuardada.getIdTracking(),
            request.idViaje(),
            ubicacionGuardada.getFechaRegistro(),
            "Ubicación registrada exitosamente"
        );
    }
    
    @Override
    @Async("virtualThreadExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public CompletableFuture<GPSIngestaResponse> registrarUbicacionAsync(GPSIngestaRequest request) {
        GPSIngestaResponse response = registrarUbicacion(request);
        return CompletableFuture.completedFuture(response);
    }
}

