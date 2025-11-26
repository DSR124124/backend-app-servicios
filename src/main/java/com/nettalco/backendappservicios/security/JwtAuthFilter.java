package com.nettalco.backendappservicios.security;

import com.nettalco.backendappservicios.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro que intercepta todas las peticiones HTTP para validar el token JWT
 * Si el token es válido, establece la autenticación en el contexto de seguridad
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Excluir endpoints públicos del filtro JWT
        return path.startsWith("/api/public/") 
            || path.startsWith("/api/versiones-terminos/actual")
            || path.startsWith("/api/versiones-terminos");
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain chain)
            throws ServletException, IOException {
        
        // Obtener el header Authorization
        String authHeader = request.getHeader("Authorization");
        
        // Verificar si existe el header y si empieza con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extraer el token (eliminar "Bearer ")
            String token = authHeader.substring(7);
            
            try {
                // Validar el token
                if (jwtUtil.validateToken(token)) {
                    // Extraer información del usuario del token
                    String username = jwtUtil.extractUsername(token);
                    Integer idUsuario = jwtUtil.extractIdUsuario(token);
                    Integer idRol = jwtUtil.extractIdRol(token);
                    String nombreRol = jwtUtil.extractNombreRol(token);
                    
                    // Crear objeto de autenticación
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + nombreRol)
                            )
                        );
                    
                    // Agregar detalles adicionales del usuario
                    authToken.setDetails(new UserDetails(idUsuario, username, idRol, nombreRol));
                    
                    // Establecer la autenticación en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                // Si hay algún error al validar el token, simplemente no autenticamos
                logger.error("Error validando token JWT: " + e.getMessage());
            }
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }
}

