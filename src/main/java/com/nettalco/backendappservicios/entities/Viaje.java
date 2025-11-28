package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "viajes")
public class Viaje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Integer idViaje;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta", nullable = false)
    private Ruta ruta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bus", nullable = false)
    private Bus bus;
    
    @Column(name = "id_conductor", nullable = false)
    private Integer idConductor; // FK lógica hacia conductores_detalle (otra BD)
    
    @Column(name = "fecha_inicio_programada")
    private OffsetDateTime fechaInicioProgramada;
    
    @Column(name = "fecha_fin_programada")
    private OffsetDateTime fechaFinProgramada;
    
    @Column(name = "fecha_inicio_real")
    private OffsetDateTime fechaInicioReal;
    
    @Column(name = "fecha_fin_real")
    private OffsetDateTime fechaFinReal;
    
    @Column(name = "estado", length = 20)
    private String estado;
    
    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = "programado";
        }
    }
    
    // Constructors
    public Viaje() {}
    
    public Viaje(Ruta ruta, Bus bus, Integer idConductor, OffsetDateTime fechaInicioProgramada, 
                OffsetDateTime fechaFinProgramada, String estado) {
        this.ruta = ruta;
        this.bus = bus;
        this.idConductor = idConductor;
        this.fechaInicioProgramada = fechaInicioProgramada;
        this.fechaFinProgramada = fechaFinProgramada;
        this.estado = estado;
    }
    
    // Getters and Setters
    public Integer getIdViaje() {
        return idViaje;
    }
    
    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }
    
    public Ruta getRuta() {
        return ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    public Bus getBus() {
        return bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }
    
    public Integer getIdConductor() {
        return idConductor;
    }
    
    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }
    
    public OffsetDateTime getFechaInicioProgramada() {
        return fechaInicioProgramada;
    }
    
    public void setFechaInicioProgramada(OffsetDateTime fechaInicioProgramada) {
        this.fechaInicioProgramada = fechaInicioProgramada;
    }
    
    public OffsetDateTime getFechaFinProgramada() {
        return fechaFinProgramada;
    }
    
    public void setFechaFinProgramada(OffsetDateTime fechaFinProgramada) {
        this.fechaFinProgramada = fechaFinProgramada;
    }
    
    public OffsetDateTime getFechaInicioReal() {
        return fechaInicioReal;
    }
    
    public void setFechaInicioReal(OffsetDateTime fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }
    
    public OffsetDateTime getFechaFinReal() {
        return fechaFinReal;
    }
    
    public void setFechaFinReal(OffsetDateTime fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
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
        Viaje viaje = (Viaje) o;
        return Objects.equals(idViaje, viaje.idViaje);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idViaje);
    }
}

