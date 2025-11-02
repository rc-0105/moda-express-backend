-- V1: tablas mínimas para MVP
CREATE TABLE IF NOT EXISTS producto (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(255) NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  descripcion TEXT,
  categoria_id BIGINT,
  imagen_url VARCHAR(512),
  activo BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS variante_producto (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  producto_id BIGINT NOT NULL,
  talla VARCHAR(20),
  color VARCHAR(50),
  stock INT DEFAULT 0,
  sku VARCHAR(100),
  FOREIGN KEY (producto_id) REFERENCES producto(id)
);
