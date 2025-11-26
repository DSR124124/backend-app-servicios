package com.nettalco.backendappservicios.servicesimplements;
import com.nettalco.backendappservicios.repositories.VersionTerminosRepository;
import com.nettalco.backendappservicios.servicesinterfaces.IVersionTerminosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VersionTerminosService implements IVersionTerminosService {
    @Autowired
    private VersionTerminosRepository termRpty;


    @Override
    public List<Object[]> findFirstByOrderByFechaVigenciaInicioDesc() {
        return termRpty.findFirstByOrderByFechaVigenciaInicioDesc();
    }
}

