package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.dtos.GPSIngestaRequest;
import com.nettalco.backendappservicios.dtos.GPSIngestaResponse;

import java.util.concurrent.CompletableFuture;

public interface IUbicacionTiempoRealService {
    
    GPSIngestaResponse registrarUbicacion(GPSIngestaRequest request);
    
    CompletableFuture<GPSIngestaResponse> registrarUbicacionAsync(GPSIngestaRequest request);
}

