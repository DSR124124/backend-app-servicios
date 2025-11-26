package com.nettalco.backendappservicios.servicesimplements;

import com.nettalco.backendappservicios.entities.VersionTerminos;
import com.nettalco.backendappservicios.repositories.VersionTerminosRepository;
import com.nettalco.backendappservicios.servicesinterfaces.IVersionTerminosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VersionTerminosService implements IVersionTerminosService {
    
    @Autowired
    private VersionTerminosRepository repository;
    
    @Override
    public List<VersionTerminos> listar() {
        return repository.findAllByOrderByFechaVigenciaInicioDesc();
    }
    
    @Override
    public Optional<VersionTerminos> obtenerPorId(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public Optional<VersionTerminos> obtenerVersionActual() {
        return repository.findByEsVersionActualTrueAndEstado(VersionTerminos.EstadoVersion.ACTIVA.name());
    }
    
    @Override
    public VersionTerminos crear(VersionTerminos version) {
        // Si es la versión actual, desactivar las anteriores
        if (version.getEsVersionActual() != null && version.getEsVersionActual()) {
            repository.findByEsVersionActualTrueAndEstado(VersionTerminos.EstadoVersion.ACTIVA.name())
                .ifPresent(versionAnterior -> {
                    versionAnterior.setEsVersionActual(false);
                    versionAnterior.setEstado(VersionTerminos.EstadoVersion.ARCHIVADA);
                    versionAnterior.setFechaVigenciaFin(version.getFechaVigenciaInicio());
                    repository.save(versionAnterior);
                });
        }
        return repository.save(version);
    }
    
    @Override
    public VersionTerminos actualizar(Integer id, VersionTerminos version) {
        Optional<VersionTerminos> versionExistente = repository.findById(id);
        if (versionExistente.isPresent()) {
            VersionTerminos versionActual = versionExistente.get();
            versionActual.setNumeroVersion(version.getNumeroVersion());
            versionActual.setTitulo(version.getTitulo());
            versionActual.setContenido(version.getContenido());
            versionActual.setResumenCambios(version.getResumenCambios());
            versionActual.setFechaVigenciaInicio(version.getFechaVigenciaInicio());
            versionActual.setFechaVigenciaFin(version.getFechaVigenciaFin());
            versionActual.setEstado(version.getEstado());
            
            // Si se marca como actual, desactivar las demás
            if (version.getEsVersionActual() != null && version.getEsVersionActual()) {
                repository.findByEsVersionActualTrueAndEstado(VersionTerminos.EstadoVersion.ACTIVA.name())
                    .ifPresent(v -> {
                        if (!v.getIdVersion().equals(id)) {
                            v.setEsVersionActual(false);
                            v.setEstado(VersionTerminos.EstadoVersion.ARCHIVADA);
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

