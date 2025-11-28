package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    
    @Query("SELECT r FROM Ruta r LEFT JOIN FETCH r.puntos WHERE r.idRuta = :id")
    Optional<Ruta> findByIdWithPuntos(@Param("id") Integer id);
    
    boolean existsByNombre(String nombre);
}

