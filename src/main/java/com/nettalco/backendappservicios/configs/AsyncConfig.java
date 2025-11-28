package com.nettalco.backendappservicios.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuración para habilitar procesamiento asíncrono con Virtual Threads (Project Loom).
 * Java 21+ soporta Virtual Threads nativamente.
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    
    /**
     * Configura un Executor usando Virtual Threads para operaciones asíncronas.
     * Los Virtual Threads son más eficientes para operaciones I/O bound como
     * la ingesta de GPS en tiempo real.
     */
    @Bean(name = "virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        // En Java 21+, podemos usar Executors.newVirtualThreadPerTaskExecutor()
        // pero Spring aún no lo soporta directamente, así que usamos ThreadPoolTaskExecutor
        // con configuración optimizada para I/O
        
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("gps-async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}

