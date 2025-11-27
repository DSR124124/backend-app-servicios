package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.VersionPrivacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VersionPrivacidadRepository extends JpaRepository<VersionPrivacidad, Integer> {
    
    Optional<VersionPrivacidad> findByEsVersionActualTrueAndEstado(VersionPrivacidad.EstadoVersion estado);
    
    List<VersionPrivacidad> findAllByOrderByFechaVigenciaInicioDesc();
    
    @Query(value = "SELECT * FROM versiones_privacidad WHERE estado::text = 'ACTIVA' ORDER BY fecha_vigencia_inicio DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findFirstByOrderByFechaVigenciaInicioDesc();
}

