package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.VersionTerminos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionTerminosRepository extends JpaRepository<VersionTerminos, Integer> {

    @Query(value = "SELECT * FROM versiones_terminos WHERE estado::text = 'ACTIVA' ORDER BY fecha_vigencia_inicio DESC LIMIT 1", nativeQuery = true)
    List<Object[]> findFirstByOrderByFechaVigenciaInicioDesc();
}

