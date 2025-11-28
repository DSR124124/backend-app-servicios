package com.nettalco.backendappservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;

/**
 * Aplicación principal del microservicio de transporte.
 * Configurada para usar Virtual Threads (Project Loom) en Java 21+.
 * 
 * La configuración de Virtual Threads para Tomcat se realiza a través de
 * application.properties (spring.threads.virtual.enabled=true)
 */
@SpringBootApplication
public class BackendAppServiciosApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendAppServiciosApplication.class, args);
    }
    
    /**
     * Configura un AsyncTaskExecutor usando Virtual Threads para operaciones asíncronas.
     * Esto mejora el rendimiento para operaciones I/O bound como la ingesta de GPS.
     */
    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }
}
