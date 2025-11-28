package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "buses")
public class Bus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bus")
    private Integer idBus;
    
    @Column(name = "placa", unique = true, nullable = false, length = 10)
    private String placa;
    
    @Column(name = "modelo", length = 50)
    private String modelo;
    
    @Column(name = "capacidad")
    private Integer capacidad;
    
    @Column(name = "imei_gps", length = 50)
    private String imeiGps;
    
    @Column(name = "estado", length = 20)
    private String estado;
    
    @PrePersist
    protected void onCreate() {
        if (capacidad == null) {
            capacidad = 50;
        }
        if (estado == null) {
            estado = "operativo";
        }
    }
    
    // Constructors
    public Bus() {}
    
    public Bus(String placa, String modelo, Integer capacidad, String imeiGps, String estado) {
        this.placa = placa;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.imeiGps = imeiGps;
        this.estado = estado;
    }
    
    // Getters and Setters
    public Integer getIdBus() {
        return idBus;
    }
    
    public void setIdBus(Integer idBus) {
        this.idBus = idBus;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public Integer getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    
    public String getImeiGps() {
        return imeiGps;
    }
    
    public void setImeiGps(String imeiGps) {
        this.imeiGps = imeiGps;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(idBus, bus.idBus);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idBus);
    }
}

