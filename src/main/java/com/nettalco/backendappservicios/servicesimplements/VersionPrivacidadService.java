package com.nettalco.backendappservicios.servicesimplements;

import com.nettalco.backendappservicios.entities.VersionPrivacidad;
import com.nettalco.backendappservicios.repositories.VersionPrivacidadRepository;
import com.nettalco.backendappservicios.servicesinterfaces.IVersionPrivacidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VersionPrivacidadService implements IVersionPrivacidadService {
    
    @Autowired
    private VersionPrivacidadRepository repository;
    
    @Override
    public List<VersionPrivacidad> listar() {
        return repository.findAllByOrderByFechaVigenciaInicioDesc();
    }
    
    @Override
    public Optional<VersionPrivacidad> obtenerPorId(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public VersionPrivacidad crear(VersionPrivacidad version) {
        // Si es la versión actual, desactivar las anteriores
        if (version.getEsVersionActual() != null && version.getEsVersionActual()) {
            repository.findByEsVersionActualTrueAndEstado(VersionPrivacidad.EstadoVersion.ACTIVA)
                .ifPresent(versionAnterior -> {
                    versionAnterior.setEsVersionActual(false);
                    versionAnterior.setEstado(VersionPrivacidad.EstadoVersion.ARCHIVADA);
                    versionAnterior.setFechaVigenciaFin(version.getFechaVigenciaInicio());
                    repository.save(versionAnterior);
                });
        }
        return repository.save(version);
    }
    
    @Override
    public VersionPrivacidad actualizar(Integer id, VersionPrivacidad version) {
        Optional<VersionPrivacidad> versionExistente = repository.findById(id);
        if (versionExistente.isPresent()) {
            VersionPrivacidad versionActual = versionExistente.get();
            versionActual.setNumeroVersion(version.getNumeroVersion());
            versionActual.setTitulo(version.getTitulo());
            versionActual.setContenido(version.getContenido());
            versionActual.setResumenCambios(version.getResumenCambios());
            versionActual.setFechaVigenciaInicio(version.getFechaVigenciaInicio());
            versionActual.setFechaVigenciaFin(version.getFechaVigenciaFin());
            versionActual.setEstado(version.getEstado());
            
            // Si se marca como actual, desactivar las demás
            if (version.getEsVersionActual() != null && version.getEsVersionActual()) {
                repository.findByEsVersionActualTrueAndEstado(VersionPrivacidad.EstadoVersion.ACTIVA)
                    .ifPresent(v -> {
                        if (!v.getIdVersion().equals(id)) {
                            v.setEsVersionActual(false);
                            v.setEstado(VersionPrivacidad.EstadoVersion.ARCHIVADA);
                            repository.save(v);
                        }
                    });
                versionActual.setEsVersionActual(true);
            }
            
            return repository.save(versionActual);
        }
        return null;
    }
    
    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }
}

