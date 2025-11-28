package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ruta_puntos")
public class RutaPunto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_punto")
    private Integer idPunto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta", nullable = false)
    private Ruta ruta;
    
    @Column(name = "orden", nullable = false)
    private Integer orden;
    
    @Column(name = "latitud", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;
    
    @Column(name = "longitud", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;
    
    @Column(name = "nombre_paradero", length = 150)
    private String nombreParadero;
    
    @Column(name = "es_paradero_oficial")
    private Boolean esParaderoOficial;
    
    @PrePersist
    protected void onCreate() {
        if (esParaderoOficial == null) {
            esParaderoOficial = false;
        }
    }
    
    // Constructors
    public RutaPunto() {}
    
    public RutaPunto(Ruta ruta, Integer orden, BigDecimal latitud, BigDecimal longitud, 
                    String nombreParadero, Boolean esParaderoOficial) {
        this.ruta = ruta;
        this.orden = orden;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombreParadero = nombreParadero;
        this.esParaderoOficial = esParaderoOficial;
    }
    
    // Getters and Setters
    public Integer getIdPunto() {
        return idPunto;
    }
    
    public void setIdPunto(Integer idPunto) {
        this.idPunto = idPunto;
    }
    
    public Ruta getRuta() {
        return ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
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
    
    public String getNombreParadero() {
        return nombreParadero;
    }
    
    public void setNombreParadero(String nombreParadero) {
        this.nombreParadero = nombreParadero;
    }
    
    public Boolean getEsParaderoOficial() {
        return esParaderoOficial;
    }
    
    public void setEsParaderoOficial(Boolean esParaderoOficial) {
        this.esParaderoOficial = esParaderoOficial;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutaPunto rutaPunto = (RutaPunto) o;
        return Objects.equals(idPunto, rutaPunto.idPunto);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idPunto);
    }
}

