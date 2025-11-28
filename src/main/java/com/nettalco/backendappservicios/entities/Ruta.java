package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rutas")
public class Ruta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ruta")
    private Integer idRuta;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(name = "color_mapa", length = 7)
    private String colorMapa;
    
    @Column(name = "estado")
    private Boolean estado;
    
    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<RutaPunto> puntos = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        if (colorMapa == null) {
            colorMapa = "#0000FF";
        }
        if (estado == null) {
            estado = true;
        }
    }
    
    // Constructors
    public Ruta() {}
    
    public Ruta(String nombre, String descripcion, String colorMapa, Boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.colorMapa = colorMapa;
        this.estado = estado;
    }
    
    // Helper methods for bidirectional relationship
    public void addPunto(RutaPunto punto) {
        puntos.add(punto);
        punto.setRuta(this);
    }
    
    public void removePunto(RutaPunto punto) {
        puntos.remove(punto);
        punto.setRuta(null);
    }
    
    // Getters and Setters
    public Integer getIdRuta() {
        return idRuta;
    }
    
    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getColorMapa() {
        return colorMapa;
    }
    
    public void setColorMapa(String colorMapa) {
        this.colorMapa = colorMapa;
    }
    
    public Boolean getEstado() {
        return estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public List<RutaPunto> getPuntos() {
        return puntos;
    }
    
    public void setPuntos(List<RutaPunto> puntos) {
        this.puntos = puntos;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruta ruta = (Ruta) o;
        return Objects.equals(idRuta, ruta.idRuta);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idRuta);
    }
}

