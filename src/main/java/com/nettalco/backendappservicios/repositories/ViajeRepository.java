package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Integer> {
    
    @Query("SELECT v FROM Viaje v WHERE v.idViaje = :id AND v.estado = 'en_curso'")
    Optional<Viaje> findByIdAndActivo(@Param("id") Integer id);
    
    @Query("SELECT v FROM Viaje v " +
           "LEFT JOIN FETCH v.ruta r " +
           "LEFT JOIN FETCH r.puntos " +
           "LEFT JOIN FETCH v.bus " +
           "WHERE v.idViaje = :id")
    Optional<Viaje> findByIdWithRelations(@Param("id") Integer id);
    
    @Query("SELECT v FROM Viaje v " +
           "LEFT JOIN FETCH v.bus " +
           "WHERE v.ruta.idRuta = :idRuta " +
           "AND v.estado IN ('en_curso', 'programado') " +
           "ORDER BY v.fechaInicioProgramada ASC")
    List<Viaje> findByRutaIdAndActivos(@Param("idRuta") Integer idRuta);
    
    boolean existsByIdViaje(Integer idViaje);
}

