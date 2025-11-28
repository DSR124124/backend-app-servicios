package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.ConductorDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConductorDetalleRepository extends JpaRepository<ConductorDetalle, Integer> {
    
    /**
     * Busca un conductor por su ID de usuario de gestión
     * @param idUsuarioGestion El ID del usuario en el sistema de gestión
     * @return Optional con el ConductorDetalle si existe
     */
    Optional<ConductorDetalle> findByIdUsuarioGestion(Integer idUsuarioGestion);
}
