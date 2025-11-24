package com.nettalco.backendappservicios.dtos;

import java.time.LocalDateTime;

public class VersionPrivacidadResponseDTO {
    
    private Integer idVersion;
    private String numeroVersion;
    private String titulo;
    private String contenido;
    private String resumenCambios;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaVigenciaInicio;
    private LocalDateTime fechaVigenciaFin;
    private Boolean esVersionActual;
    private String estado;
    private Integer idUsuarioCreador;
    private String nombreUsuarioCreador;
    private LocalDateTime fechaModificacion;
    
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
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
}

