package com.nettalco.backendappservicios.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "versiones_terminos")
public class VersionTerminos {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_version")
    private Integer idVersion;
    
    @Column(name = "numero_version", nullable = false, length = 10)
    private String numeroVersion;
    
    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;
    
    @Column(name = "contenido", nullable = false, columnDefinition = "TEXT")
    private String contenido;
    
    @Column(name = "resumen_cambios", columnDefinition = "TEXT")
    private String resumenCambios;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_vigencia_inicio", nullable = false)
    private LocalDateTime fechaVigenciaInicio;
    
    @Column(name = "fecha_vigencia_fin")
    private LocalDateTime fechaVigenciaFin;
    
    @Column(name = "es_version_actual", nullable = false)
    private Boolean esVersionActual;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, columnDefinition = "estado_version_enum")
    private EstadoVersion estado;
    
    @Column(name = "id_usuario_creador")
    private Integer idUsuarioCreador;
    
    @Column(name = "nombre_usuario_creador", length = 100)
    private String nombreUsuarioCreador;
    
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (esVersionActual == null) {
            esVersionActual = false;
        }
        if (estado == null) {
            estado = EstadoVersion.ACTIVA;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaModificacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Integer getIdVersion() {
        return idVersion;
    }
    
    public void setIdVersion(Integer idVersion) {
        this.idVersion = idVersion;
    }
    
    public String getNumeroVersion() {
        return numeroVersion;
    }
    
    public void setNumeroVersion(String numeroVersion) {
        this.numeroVersion = numeroVersion;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public String getResumenCambios() {
        return resumenCambios;
    }
    
    public void setResumenCambios(String resumenCambios) {
        this.resumenCambios = resumenCambios;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaVigenciaInicio() {
        return fechaVigenciaInicio;
    }
    
    public void setFechaVigenciaInicio(LocalDateTime fechaVigenciaInicio) {
        this.fechaVigenciaInicio = fechaVigenciaInicio;
    }
    
    public LocalDateTime getFechaVigenciaFin() {
        return fechaVigenciaFin;
    }
    
    public void setFechaVigenciaFin(LocalDateTime fechaVigenciaFin) {
        this.fechaVigenciaFin = fechaVigenciaFin;
    }
    
    public Boolean getEsVersionActual() {
        return esVersionActual;
    }
    
    public void setEsVersionActual(Boolean esVersionActual) {
        this.esVersionActual = esVersionActual;
    }
    
    public EstadoVersion getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoVersion estado) {
        this.estado = estado;
    }
    
    public Integer getIdUsuarioCreador() {
        return idUsuarioCreador;
    }
    
    public void setIdUsuarioCreador(Integer idUsuarioCreador) {
        this.idUsuarioCreador = idUsuarioCreador;
    }
    
    public String getNombreUsuarioCreador() {
        return nombreUsuarioCreador;
    }
    
    public void setNombreUsuarioCreador(String nombreUsuarioCreador) {
        this.nombreUsuarioCreador = nombreUsuarioCreador;
    }
    
    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public enum EstadoVersion {
        ACTIVA, INACTIVA, ARCHIVADA
    }
}

