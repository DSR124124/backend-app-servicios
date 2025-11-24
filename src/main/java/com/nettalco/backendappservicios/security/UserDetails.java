package com.nettalco.backendappservicios.security;

/**
 * Clase auxiliar para almacenar detalles del usuario autenticado
 * Esta información se extrae del token JWT
 */
public class UserDetails {
    private final Integer idUsuario;
    private final String username;
    private final Integer idRol;
    private final String nombreRol;
    
    public UserDetails(Integer idUsuario, String username, Integer idRol, String nombreRol) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }
    
    public Integer getIdUsuario() {
        return idUsuario;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Integer getIdRol() {
        return idRol;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    @Override
    public String toString() {
        return "UserDetails{" +
                "idUsuario=" + idUsuario +
                ", username='" + username + '\'' +
                ", idRol=" + idRol +
                ", nombreRol='" + nombreRol + '\'' +
                '}';
    }
}

