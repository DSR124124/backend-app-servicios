# 🚀 Backend de Servicios - Nettalco

Backend de servicios con validación JWT para la arquitectura dual backend.

## 📋 Descripción

Este backend es parte de una arquitectura dual donde:
- **Backend de Gestión**: Maneja autenticación, roles y permisos
- **Backend de Servicios**: Maneja la lógica de negocio y CRUD (este proyecto)

## 🔧 Configuración

### Requisitos Previos
- Java 17+
- Maven 3.6+
- MySQL 8.0+ (o PostgreSQL si lo prefieres)
- Backend de Gestión en ejecución

### Base de Datos

1. Crear la base de datos:
```sql
CREATE DATABASE bd_servicios;
```

2. Configurar en `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bd_servicios
spring.datasource.username=root
spring.datasource.password=tupassword
```

### JWT Secret Key

**IMPORTANTE**: La `jwt.secret` debe ser **EXACTAMENTE LA MISMA** que en el Backend de Gestión:

```properties
jwt.secret=Y2Y4ZjE2NzM5YjQ4ZTNhMjVkNGI2YzVmODcwMTIzNDU2Nzg5MGFiY2RlZjEwMjM0NTY3ODkwYWJjZGVmMTIzNDU2Nzg5MGFiY2RlZjEyMzQ1Njc4OTBhYmNkZWY=
```

### Puerto

El backend de servicios corre en el puerto **8081** (el de gestión usa 8080):
```properties
server.port=8081
```

## 🚀 Ejecutar el Proyecto

### Opción 1: Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Opción 2: IDE
Ejecutar la clase `BackendAppServiciosApplication.java`

El servidor estará disponible en: `http://localhost:8081`

## 🧪 Probar el Backend

### 1. Obtener Token JWT del Backend de Gestión

Primero, necesitas autenticarte en el backend de gestión:

```bash
curl -X POST http://154.38.186.149:8080/gestion/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "password123"
  }'
```

Respuesta (guarda el `token`):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "usuario": {
    "idUsuario": 1,
    "username": "admin",
    "rol": "Administrador"
  }
}
```

### 2. Usar el Token en el Backend de Servicios

#### Obtener Perfil (Requiere Token)

```bash
curl http://localhost:8081/api/clientes/mi-perfil \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

Respuesta:
```json
{
  "idUsuario": 1,
  "username": "admin",
  "rol": "Administrador",
  "mensaje": "Token válido - Usuario autenticado"
}
```

#### Listar Clientes (Requiere Token)

```bash
curl http://localhost:8081/api/clientes \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

#### Crear Cliente (Solo Administradores)

```bash
curl -X POST http://localhost:8081/api/clientes \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Cliente Nuevo",
    "email": "cliente@ejemplo.com"
  }'
```

#### Actualizar Cliente (Administradores y Gestores)

```bash
curl -X PUT http://localhost:8081/api/clientes/1 \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Cliente Actualizado",
    "email": "actualizado@ejemplo.com"
  }'
```

#### Eliminar Cliente (Solo Administradores)

```bash
curl -X DELETE http://localhost:8081/api/clientes/1 \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

## 🔐 Seguridad

### Endpoints Públicos (Sin Token)
- `GET /api/public/**` - Información pública

### Endpoints Protegidos (Requieren Token)
- `GET /api/clientes/mi-perfil` - Cualquier usuario autenticado
- `GET /api/clientes` - Cualquier usuario autenticado
- `GET /api/clientes/{id}` - Cualquier usuario autenticado
- `POST /api/clientes` - Solo Administradores
- `PUT /api/clientes/{id}` - Administradores y Gestores
- `DELETE /api/clientes/{id}` - Solo Administradores

### Validación de Roles

El backend valida automáticamente:
1. **Token válido**: Firma y expiración
2. **Información del usuario**: Extrae del token
3. **Roles**: Valida permisos en cada endpoint

```java
// Ejemplo de validación de rol en un controlador
UserDetails userDetails = getUserDetails();

if (!"Administrador".equals(userDetails.getNombreRol())) {
    return ResponseEntity.status(403)
        .body(Map.of("error", "No tienes permisos"));
}
```

## 📦 Estructura del Proyecto

```
backend-app-servicios/
├── src/main/java/com/nettalco/backendappservicios/
│   ├── BackendAppServiciosApplication.java  # Clase principal
│   ├── configs/
│   │   └── SecurityConfig.java             # Configuración de seguridad
│   ├── security/
│   │   ├── JwtAuthFilter.java              # Filtro de autenticación JWT
│   │   └── UserDetails.java                # Detalles del usuario
│   ├── util/
│   │   └── JwtUtil.java                    # Utilidad para JWT
│   ├── controllers/
│   │   └── ClienteController.java          # Ejemplo de controlador
│   ├── entities/                           # Tus entidades JPA
│   ├── repositories/                       # Repositorios Spring Data
│   ├── servicesinterfaces/                 # Interfaces de servicios
│   └── servicesimplements/                 # Implementaciones de servicios
├── src/main/resources/
│   └── application.properties              # Configuración
└── pom.xml                                 # Dependencias Maven
```

## 🔑 Flujo de Autenticación

```
1. Flutter App → Login → Backend Gestión (puerto 8080)
                          ↓
                      JWT Token
                          ↓
2. Flutter App → Servicios → Backend Servicios (puerto 8081)
   (con Token en header)         ↓
                          Valida Token
                          (misma secret key)
                          ↓
                     Procesa Request
                          ↓
                     Retorna Datos
```

## ⚠️ Errores Comunes

### 401 Unauthorized
- Token no proporcionado
- Token expirado
- Token inválido
- Secret key diferente entre backends

### 403 Forbidden
- Usuario autenticado pero sin permisos para la acción
- Rol insuficiente para el endpoint

### Solución:
1. Verificar que la `jwt.secret` sea la misma en ambos backends
2. Verificar que el token no haya expirado (24 horas por defecto)
3. Verificar que el header `Authorization` tenga el formato: `Bearer TOKEN`

## 📝 Crear Nuevos Endpoints

Para crear un nuevo endpoint protegido:

```java
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    private UserDetails getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getDetails();
    }
    
    @GetMapping
    public ResponseEntity<?> listarProductos() {
        UserDetails userDetails = getUserDetails();
        
        // Tu lógica aquí
        // Puedes acceder a:
        // - userDetails.getIdUsuario()
        // - userDetails.getUsername()
        // - userDetails.getIdRol()
        // - userDetails.getNombreRol()
        
        return ResponseEntity.ok(datos);
    }
}
```

## 🐳 Docker (Opcional)

Si quieres dockerizar el servicio:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/backend-app-servicios-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
mvn clean package
docker build -t backend-servicios .
docker run -p 8081:8081 backend-servicios
```

## 📞 Soporte

Para más información, consulta el archivo `ARQUITECTURA_DUAL_BACKEND.md` en la raíz del proyecto.

---

**¡Listo para usar!** 🎉

