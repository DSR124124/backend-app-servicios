package com.nettalco.backendappservicios.repositories;

import com.nettalco.backendappservicios.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    
    Optional<Bus> findByPlaca(String placa);
    
    boolean existsByPlaca(String placa);
}

