package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.VersionTerminos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VersionTerminosRepository extends JpaRepository<VersionTerminos, Integer> {
    
    @Query(value = "SELECT * FROM versiones_terminos WHERE es_version_actual = true AND estado::text = :estado", nativeQuery = true)
    Optional<VersionTerminos> findByEsVersionActualTrueAndEstado(@Param("estado") String estado);
    
    List<VersionTerminos> findAllByOrderByFechaVigenciaInicioDesc();
}

