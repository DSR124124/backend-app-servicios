-- ============================================
-- Script para crear tabla de versiones de 
-- Términos y Condiciones
-- PostgreSQL Version
-- ============================================

-- Conectar a la base de datos
-- \c bd_servicios;

-- ============================================
-- Crear tipo ENUM para estado (si no existe ya)
-- ============================================

DO $$ BEGIN
    CREATE TYPE estado_version_enum AS ENUM ('ACTIVA', 'INACTIVA', 'ARCHIVADA');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- ============================================
-- Eliminar tabla si existe (solo para desarrollo)
-- ============================================
-- DROP TABLE IF EXISTS versiones_terminos CASCADE;

-- ============================================
-- Crear tabla de versiones de Términos y Condiciones
-- ============================================

CREATE TABLE IF NOT EXISTS versiones_terminos (
    id_version SERIAL PRIMARY KEY,
    
    -- Número de versión (ej: 1.0, 1.1, 2.0, etc.)
    numero_version VARCHAR(10) NOT NULL,
    
    -- Título de la versión
    titulo VARCHAR(255) NOT NULL,
    
    -- Contenido completo del documento (TEXT para documentos largos)
    contenido TEXT NOT NULL,
    
    -- Resumen o cambios principales de esta versión
    resumen_cambios TEXT,
    
    -- Fecha de creación de esta versión
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Fecha desde la cual esta versión entra en vigencia
    fecha_vigencia_inicio TIMESTAMP NOT NULL,
    
    -- Fecha hasta la cual esta versión está vigente (NULL = vigente indefinidamente)
    fecha_vigencia_fin TIMESTAMP NULL,
    
    -- Indica si esta es la versión actual/activa
    es_version_actual BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Estado: 'ACTIVA', 'INACTIVA', 'ARCHIVADA'
    estado estado_version_enum NOT NULL DEFAULT 'ACTIVA',
    
    -- Usuario que creó esta versión (ID del usuario del backend de gestión)
    id_usuario_creador INTEGER,
    
    -- Nombre del usuario que creó esta versión (para referencia)
    nombre_usuario_creador VARCHAR(100),
    
    -- Fecha de última modificación
    fecha_modificacion TIMESTAMP NULL DEFAULT NULL
    
    -- Nota: En PostgreSQL, ON UPDATE CURRENT_TIMESTAMP se maneja con triggers
);

-- ============================================
-- Crear índices para mejorar rendimiento
-- ============================================

CREATE INDEX IF NOT EXISTS idx_terminos_estado 
    ON versiones_terminos(estado);

CREATE INDEX IF NOT EXISTS idx_terminos_es_version_actual 
    ON versiones_terminos(es_version_actual);

CREATE INDEX IF NOT EXISTS idx_terminos_fecha_vigencia 
    ON versiones_terminos(fecha_vigencia_inicio, fecha_vigencia_fin);

-- ============================================
-- Constraint: Solo puede haber una versión actual
-- ============================================

-- Crear índice único parcial para asegurar solo una versión actual
CREATE UNIQUE INDEX IF NOT EXISTS uk_terminos_version_actual 
    ON versiones_terminos(es_version_actual) 
    WHERE es_version_actual = TRUE AND estado = 'ACTIVA';

-- ============================================
-- Comentarios en la tabla y columnas
-- ============================================

COMMENT ON TABLE versiones_terminos IS 
    'Tabla para gestionar versiones de Términos y Condiciones';

COMMENT ON COLUMN versiones_terminos.id_version IS 
    'Identificador único de la versión';

COMMENT ON COLUMN versiones_terminos.numero_version IS 
    'Número de versión (ej: 1.0, 1.1, 2.0)';

COMMENT ON COLUMN versiones_terminos.contenido IS 
    'Contenido completo del documento de términos y condiciones';

COMMENT ON COLUMN versiones_terminos.es_version_actual IS 
    'Indica si esta es la versión actual activa';

-- ============================================
-- Datos de ejemplo (opcional)
-- ============================================

-- Insertar versión inicial de Términos y Condiciones
INSERT INTO versiones_terminos (
    numero_version,
    titulo,
    contenido,
    resumen_cambios,
    fecha_vigencia_inicio,
    es_version_actual,
    estado,
    id_usuario_creador,
    nombre_usuario_creador
) VALUES (
    '1.0',
    'Términos y Condiciones - Versión Inicial',
    'Este es el contenido completo de los términos y condiciones versión 1.0. Aquí se detallan todos los términos de uso de la aplicación...',
    'Versión inicial de los términos y condiciones',
    NOW(),
    TRUE,
    'ACTIVA',
    1,
    'Administrador'
) ON CONFLICT DO NOTHING;

--