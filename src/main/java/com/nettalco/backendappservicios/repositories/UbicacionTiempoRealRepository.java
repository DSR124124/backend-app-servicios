package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.UbicacionTiempoReal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface UbicacionTiempoRealRepository extends JpaRepository<UbicacionTiempoReal, Long> {
    
    @Query("SELECT u FROM UbicacionTiempoReal u WHERE u.viaje.idViaje = :idViaje ORDER BY u.fechaRegistro DESC")
    List<UbicacionTiempoReal> findByViajeIdOrderByFechaRegistroDesc(@Param("idViaje") Integer idViaje);
    
    @Query("SELECT u FROM UbicacionTiempoReal u WHERE u.viaje.idViaje = :idViaje AND u.fechaRegistro >= :fechaDesde ORDER BY u.fechaRegistro ASC")
    List<UbicacionTiempoReal> findByViajeIdAndFechaRegistroAfter(
        @Param("idViaje") Integer idViaje, 
        @Param("fechaDesde") OffsetDateTime fechaDesde
    );
    
    @Modifying
    @Query("DELETE FROM UbicacionTiempoReal u WHERE u.fechaRegistro < :fechaAntes")
    void deleteByFechaRegistroBefore(@Param("fechaAntes") OffsetDateTime fechaAntes);
}

