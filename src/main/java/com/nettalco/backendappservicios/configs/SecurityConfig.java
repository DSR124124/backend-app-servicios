package com.nettalco.backendappservicios.configs;

import com.nettalco.backendappservicios.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de seguridad para el backend de servicios
 * Define qué endpoints requieren autenticación y configura CORS
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configurar CORS
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // Deshabilitar CSRF (no necesario para API REST con JWT)
            .csrf(csrf -> csrf.disable())
            
            // Política de sesión: STATELESS (sin sesiones)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Configurar autorización de requests
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (sin autenticación)
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/health").permitAll() // Health check simple público
                .requestMatchers("/api/clientes/health").permitAll() // Health check público
                .requestMatchers("/api/versiones-terminos/actual").permitAll() // Términos y condiciones público
                .requestMatchers("/api/versiones-terminos/**").permitAll() // Todas las versiones de términos públicas
                .requestMatchers("/api/versiones-privacidad/actual").permitAll() // Política de privacidad público
                .requestMatchers("/api/versiones-privacidad/**").permitAll() // Todas las versiones de privacidad públicas
                .requestMatchers("/actuator/health").permitAll() // Health check de Spring Boot Actuator (si está habilitado)
                
                // Endpoints de transporte (públicos para acceso sin restricciones)
                // NOTA: Para producción, considerar autenticación o tokens específicos para GPS
                .requestMatchers("/api/gps/**").permitAll() // GPS público para alto throughput
                .requestMatchers("/api/rutas/**").permitAll() // Rutas públicas para acceso libre
                .requestMatchers("/api/trips/**").permitAll() // Viajes públicos para seguimiento
                
                // Todos los demás endpoints requieren autenticación
                .anyRequest().authenticated()
            )
            
            // Agregar el filtro JWT antes del filtro de autenticación estándar
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    /**
     * Configuración de CORS para permitir peticiones desde Flutter y web
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir todos los orígenes usando allowedOriginPatterns (compatible con allowCredentials)
        // Para producción, especificar dominios específicos:
        // configuration.setAllowedOrigins(Arrays.asList("https://edugen.brianuceda.xyz", "https://app.example.com"));
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Permitir todos los métodos HTTP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        
        // Permitir todos los headers importantes
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Headers expuestos al cliente
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        
        // No permitir credenciales cuando se usa "*" en origins
        // Si necesitas credenciales, especifica dominios específicos arriba
        configuration.setAllowCredentials(false);
        
        // Cache de preflight requests (1 hora)
        configuration.setMaxAge(3600L);
        
        // Aplicar configuración a todos los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}

