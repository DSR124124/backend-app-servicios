package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.entities.VersionTerminos;
import java.util.List;
import java.util.Optional;

public interface IVersionTerminosService {
    
    List<VersionTerminos> listar();
    
    Optional<VersionTerminos> obtenerPorId(Integer id);
    
    Optional<VersionTerminos> obtenerVersionActual();
    
    VersionTerminos crear(VersionTerminos version);
    
    VersionTerminos actualizar(Integer id, VersionTerminos version);
    
    void eliminar(Integer id);
}

