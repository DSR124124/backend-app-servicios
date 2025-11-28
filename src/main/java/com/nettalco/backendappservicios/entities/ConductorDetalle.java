package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "conductores_detalle")
public class ConductorDetalle {
    
    @Id
    @Column(name = "id_usuario_gestion")
    private Integer idUsuarioGestion; // FK lógica hacia otra BD
    
    @Column(name = "licencia_numero", nullable = false, length = 20)
    private String licenciaNumero;
    
    @Column(name = "categoria", length = 20)
    private String categoria;
    
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;
    
    @Column(name = "estado", length = 20)
    private String estado;
    
    @PrePersist
    protected void onCreate() {
        if (categoria == null) {
            categoria = "A-IIIC";
        }
        if (estado == null) {
            estado = "activo";
        }
    }
    
    // Constructors
    public ConductorDetalle() {}
    
    public ConductorDetalle(Integer idUsuarioGestion, String licenciaNumero, String categoria, 
                           String telefonoContacto, String estado) {
        this.idUsuarioGestion = idUsuarioGestion;
        this.licenciaNumero = licenciaNumero;
        this.categoria = categoria;
        this.telefonoContacto = telefonoContacto;
        this.estado = estado;
    }
    
    // Getters and Setters
    public Integer getIdUsuarioGestion() {
        return idUsuarioGestion;
    }
    
    public void setIdUsuarioGestion(Integer idUsuarioGestion) {
        this.idUsuarioGestion = idUsuarioGestion;
    }
    
    public String getLicenciaNumero() {
        return licenciaNumero;
    }
    
    public void setLicenciaNumero(String licenciaNumero) {
        this.licenciaNumero = licenciaNumero;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getTelefonoContacto() {
        return telefonoContacto;
    }
    
    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
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
        ConductorDetalle that = (ConductorDetalle) o;
        return Objects.equals(idUsuarioGestion, that.idUsuarioGestion);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idUsuarioGestion);
    }
}

