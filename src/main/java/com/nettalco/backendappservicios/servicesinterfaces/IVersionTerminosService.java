package com.nettalco.backendappservicios.servicesinterfaces;

import com.nettalco.backendappservicios.entities.VersionTerminos;
import java.util.Optional;
import java.util.List;
public interface IVersionTerminosService {

    List<Object[]>findFirstByOrderByFechaVigenciaInicioDesc();
}

