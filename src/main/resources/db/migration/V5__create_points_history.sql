-- V5: points history table
CREATE TABLE IF NOT EXISTS puntos_historial (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  pedido_id BIGINT NULL,
  puntos INT NOT NULL,
  motivo VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  KEY ix_puntos_usuario (usuario_id)
);
