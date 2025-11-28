package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "ubicaciones_tiempo_real", indexes = {
    @Index(name = "idx_gps_viaje", columnList = "id_viaje"),
    @Index(name = "idx_gps_fecha", columnList = "fecha_registro")
})
public class UbicacionTiempoReal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tracking")
    private Long idTracking;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_viaje", nullable = false)
    private Viaje viaje;
    
    @Column(name = "latitud", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;
    
    @Column(name = "longitud", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;
    
    @Column(name = "velocidad_kmh", precision = 5, scale = 2)
    private BigDecimal velocidadKmh;
    
    @Column(name = "rumbo", precision = 5, scale = 2)
    private BigDecimal rumbo;
    
    @Column(name = "fecha_registro", nullable = false)
    private OffsetDateTime fechaRegistro;
    
    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = OffsetDateTime.now();
        }
    }
    
    // Constructors
    public UbicacionTiempoReal() {}
    
    public UbicacionTiempoReal(Viaje viaje, BigDecimal latitud, BigDecimal longitud, 
                               BigDecimal velocidadKmh, BigDecimal rumbo, OffsetDateTime fechaRegistro) {
        this.viaje = viaje;
        this.latitud = latitud;
        this.longitud = longitud;
        this.velocidadKmh = velocidadKmh;
        this.rumbo = rumbo;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Getters and Setters
    public Long getIdTracking() {
        return idTracking;
    }
    
    public void setIdTracking(Long idTracking) {
        this.idTracking = idTracking;
    }
    
    public Viaje getViaje() {
        return viaje;
    }
    
    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
    
    public BigDecimal getLatitud() {
        return latitud;
    }
    
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }
    
    public BigDecimal getLongitud() {
        return longitud;
    }
    
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }
    
    public BigDecimal getVelocidadKmh() {
        return velocidadKmh;
    }
    
    public void setVelocidadKmh(BigDecimal velocidadKmh) {
        this.velocidadKmh = velocidadKmh;
    }
    
    public BigDecimal getRumbo() {
        return rumbo;
    }
    
    public void setRumbo(BigDecimal rumbo) {
        this.rumbo = rumbo;
    }
    
    public OffsetDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(OffsetDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UbicacionTiempoReal that = (UbicacionTiempoReal) o;
        return Objects.equals(idTracking, that.idTracking);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idTracking);
    }
}

