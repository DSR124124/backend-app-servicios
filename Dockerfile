# ============================================
# Dockerfile para Backend de Servicios
# Optimizado para Dockploy
# ============================================

# Etapa 1: Build
FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de Maven para aprovechar cache de capas
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se cachea esta capa)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación (sin tests para producción)
RUN ./mvnw clean package -DskipTests -B

# ============================================
# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

# Instalar wget para health check
RUN apk add --no-cache wget

# Metadatos del contenedor
LABEL maintainer="Backend Servicios"
LABEL description="Backend de Servicios - Sistema de Lógica de Negocio y CRUD"
LABEL version="1.0"

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar propietario del archivo
RUN chown spring:spring app.jar

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto 8081 (Spring Boot escucha directamente aquí)
EXPOSE 8081

# Variables de entorno por defecto (se pueden sobrescribir en Dockploy)
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0" \
    SERVER_ADDRESS="0.0.0.0" \
    SERVER_PORT="8081" \
    SPRING_PROFILES_ACTIVE="prod"

# Health check para Docker (más tiempo de inicio para Spring Boot)
# IMPORTANTE: Usar el context-path /servicios en el health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/servicios/api/clientes/health || exit 1

# Comando para ejecutar la aplicación
# Asegurar que escucha en 0.0.0.0 para ser accesible desde fuera del contenedor
# Usar variables de entorno para flexibilidad, pero con valores por defecto seguros
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dserver.address=${SERVER_ADDRESS:-0.0.0.0} -Dserver.port=${SERVER_PORT:-8081} -jar app.jar"]

