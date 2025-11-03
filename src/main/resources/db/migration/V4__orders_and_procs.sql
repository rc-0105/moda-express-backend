-- V4: orders tables and stored procedures
-- Tables: pedido, detalle_pedido, pago, envio, fidelizacion

CREATE TABLE IF NOT EXISTS pedido (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  total DECIMAL(12,2) DEFAULT 0,
  estado VARCHAR(50) DEFAULT 'PENDIENTE',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id BIGINT NOT NULL,
  variante_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(12,2) NOT NULL,
  FOREIGN KEY (pedido_id) REFERENCES pedido(id),
  FOREIGN KEY (variante_id) REFERENCES variante_producto(id)
);

CREATE TABLE IF NOT EXISTS pago (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id BIGINT NOT NULL,
  metodo VARCHAR(100),
  monto DECIMAL(12,2),
  paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

CREATE TABLE IF NOT EXISTS envio (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pedido_id BIGINT NOT NULL,
  direccion VARCHAR(512),
  estado VARCHAR(50),
  shipped_at TIMESTAMP,
  FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

CREATE TABLE IF NOT EXISTS fidelizacion (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  usuario_id BIGINT NOT NULL,
  puntos INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$

-- stub: asignar puntos
CREATE PROCEDURE IF NOT EXISTS sp_asignar_puntos_fidelizacion(IN p_usuario_id BIGINT, IN p_puntos INT)
BEGIN
  INSERT INTO fidelizacion (usuario_id, puntos) VALUES (p_usuario_id, p_puntos);
END$$

-- crear pedido completo: recibe json detalles y retorna pedido_id y estado
CREATE PROCEDURE IF NOT EXISTS sp_crear_pedido_completo(
  IN p_usuario_id BIGINT,
  IN p_metodo_pago VARCHAR(100),
  IN p_detalles_json JSON,
  OUT p_pedido_id BIGINT,
  OUT p_estado VARCHAR(50)
)
BEGIN
  DECLARE v_total DECIMAL(12,2) DEFAULT 0;
  DECLARE v_variante_id BIGINT;
  DECLARE v_cantidad INT;
  DECLARE v_precio DECIMAL(12,2);

  -- Calculate total from JSON
  SELECT SUM(t.cantidad * t.precio_unitario)
    INTO v_total
  FROM JSON_TABLE(p_detalles_json, '$[*]'
    COLUMNS(
      cantidad INT PATH '$.cantidad',
      precio_unitario DECIMAL(12,2) PATH '$.precio_unitario'
    )) AS t;

  -- insert pedido
  INSERT INTO pedido (usuario_id, total, estado) VALUES (p_usuario_id, v_total, 'CONFIRMADO');
  SET p_pedido_id = LAST_INSERT_ID();

  -- insert detalle and update stock
  INSERT INTO detalle_pedido (pedido_id, variante_id, cantidad, precio_unitario)
    SELECT p_pedido_id, jt.variante_id, jt.cantidad, jt.precio_unitario
    FROM JSON_TABLE(p_detalles_json, '$[*]'
      COLUMNS(
        variante_id BIGINT PATH '$.variante_id',
        cantidad INT PATH '$.cantidad',
        precio_unitario DECIMAL(12,2) PATH '$.precio_unitario'
      )) AS jt;

  -- Update stock per variant
  -- iterate JSON to update stock
  DECLARE cur_cursor CURSOR FOR
    SELECT jt.variante_id, jt.cantidad
    FROM JSON_TABLE(p_detalles_json, '$[*]'
      COLUMNS(
        variante_id BIGINT PATH '$.variante_id',
        cantidad INT PATH '$.cantidad'
      )) AS jt;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_variante_id = NULL;

  OPEN cur_cursor;
  read_loop: LOOP
    FETCH cur_cursor INTO v_variante_id, v_cantidad;
    IF v_variante_id IS NULL THEN
      LEAVE read_loop;
    END IF;
    UPDATE variante_producto SET stock = stock - v_cantidad WHERE id = v_variante_id;
  END LOOP;
  CLOSE cur_cursor;

  -- insert pago (simple)
  INSERT INTO pago (pedido_id, metodo, monto) VALUES (p_pedido_id, p_metodo_pago, v_total);

  -- insert envio placeholder
  INSERT INTO envio (pedido_id, direccion, estado) VALUES (p_pedido_id, NULL, 'PENDIENTE');

  -- assign loyalty points (simple: 1 point per 100 units)
  DECLARE v_puntos INT DEFAULT 0;
  SET v_puntos = FLOOR(v_total / 100);
  CALL sp_asignar_puntos_fidelizacion(p_usuario_id, v_puntos);

  SET p_estado = 'CONFIRMADO';
END$$

-- obtener detalle pedido (returns joined rows)
CREATE PROCEDURE IF NOT EXISTS sp_obtener_detalle_pedido(IN p_pedido_id BIGINT)
BEGIN
  SELECT p.id as pedido_id, p.usuario_id, p.total, p.estado, p.created_at,
         dp.id as detalle_id, dp.variante_id, dp.cantidad, dp.precio_unitario,
         pay.id as pago_id, pay.metodo as pago_metodo, pay.monto as pago_monto, pay.paid_at,
         sh.id as envio_id, sh.direccion as envio_direccion, sh.estado as envio_estado, sh.shipped_at
  FROM pedido p
  LEFT JOIN detalle_pedido dp ON dp.pedido_id = p.id
  LEFT JOIN pago pay ON pay.pedido_id = p.id
  LEFT JOIN envio sh ON sh.pedido_id = p.id
  WHERE p.id = p_pedido_id;
END$$

DELIMITER ;
