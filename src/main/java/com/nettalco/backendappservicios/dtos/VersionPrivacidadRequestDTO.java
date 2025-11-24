package com.nettalco.backendappservicios.dtos;

import java.time.LocalDateTime;

public class VersionPrivacidadRequestDTO {
    
    private String numeroVersion;
    private String titulo;
    private String contenido;
    private String resumenCambios;
    private LocalDateTime fechaVigenciaInicio;
    private LocalDateTime fechaVigenciaFin;
    private Boolean esVersionActual;
    private String estado;
    
    // Getters y Setters
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
}

