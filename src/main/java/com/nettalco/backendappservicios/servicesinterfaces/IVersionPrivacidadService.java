package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.entities.VersionPrivacidad;
import java.util.List;
import java.util.Optional;

public interface IVersionPrivacidadService {
    
    List<VersionPrivacidad> listar();
    
    Optional<VersionPrivacidad> obtenerPorId(Integer id);
    
    VersionPrivacidad crear(VersionPrivacidad version);
    
    VersionPrivacidad actualizar(Integer id, VersionPrivacidad version);
    
    void eliminar(Integer id);
}

