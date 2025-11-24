-- ============================================
-- Script para crear tabla de versiones de 
-- Políticas de Privacidad
-- PostgreSQL Version
-- ============================================

-- Conectar a la base de datos
-- \c bd_servicios;

-- ============================================
-- Crear tipo ENUM para estado
-- ============================================

DO $$ BEGIN
    CREATE TYPE estado_version_enum AS ENUM ('ACTIVA', 'INACTIVA', 'ARCHIVADA');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- ============================================
-- Eliminar tabla si existe (solo para desarrollo)
-- ============================================
-- DROP TABLE IF EXISTS versiones_privacidad CASCADE;

-- ============================================
-- Crear tabla de versiones de Políticas de Privacidad
-- ============================================

CREATE TABLE IF NOT EXISTS versiones_privacidad (
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

CREATE INDEX IF NOT EXISTS idx_privacidad_estado 
    ON versiones_privacidad(estado);

CREATE INDEX IF NOT EXISTS idx_privacidad_es_version_actual 
    ON versiones_privacidad(es_version_actual);

CREATE INDEX IF NOT EXISTS idx_privacidad_fecha_vigencia 
    ON versiones_privacidad(fecha_vigencia_inicio, fecha_vigencia_fin);

-- ============================================
-- Constraint: Solo puede haber una versión actual
-- ============================================

-- Crear índice único parcial para asegurar solo una versión actual
CREATE UNIQUE INDEX IF NOT EXISTS uk_privacidad_version_actual 
    ON versiones_privacidad(es_version_actual) 
    WHERE es_version_actual = TRUE AND estado = 'ACTIVA';

-- ============================================
-- Comentarios en la tabla y columnas
-- ============================================

COMMENT ON TABLE versiones_privacidad IS 
    'Tabla para gestionar versiones de Políticas de Privacidad';

COMMENT ON COLUMN versiones_privacidad.id_version IS 
    'Identificador único de la versión';

COMMENT ON COLUMN versiones_privacidad.numero_version IS 
    'Número de versión (ej: 1.0, 1.1, 2.0)';

COMMENT ON COLUMN versiones_privacidad.contenido IS 
    'Contenido completo del documento de política de privacidad';

COMMENT ON COLUMN versiones_privacidad.es_version_actual IS 
    'Indica si esta es la versión actual activa';

-- ============================================
-- Datos de ejemplo (opcional)
-- ============================================

-- Insertar versión inicial de Política de Privacidad
INSERT INTO versiones_privacidad (
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
    'Política de Privacidad - Versión Inicial',
    'Este es el contenido completo de la política de privacidad versión 1.0. Aquí se detallan todos los términos y condiciones sobre cómo se manejan los datos personales de los usuarios...',
    'Versión inicial de la política de privacidad',
    NOW(),
    TRUE,
    'ACTIVA',
    1,
    'Administrador'
) ON CONFLICT DO NOTHING;
