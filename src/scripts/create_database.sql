-- scripts/create_database.sql
CREATE DATABASE crud_db;

\c crud_db;

CREATE TABLE personas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    telefono VARCHAR(20),
    edad INTEGER,
    direccion TEXT
);

-- Datos de ejemplo
INSERT INTO personas (nombre, apellido, email, telefono, edad, direccion) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', '123-456-7890', 30, 'Calle Principal 123'),
('María', 'García', 'maria.garcia@email.com', '123-456-7891', 25, 'Avenida Central 456'),
('Carlos', 'López', 'carlos.lopez@email.com', '123-456-7892', 35, 'Plaza Mayor 789');
