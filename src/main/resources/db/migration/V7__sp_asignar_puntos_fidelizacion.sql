-- V7: stored procedure to assign loyalty points for a given pedido
DROP PROCEDURE IF EXISTS sp_asignar_puntos_fidelizacion;
DELIMITER //
CREATE PROCEDURE sp_asignar_puntos_fidelizacion(
  IN p_pedido_id BIGINT,
  OUT p_puntos_asignados INT,
  OUT p_msg VARCHAR(255)
)
BEGIN
  DECLARE v_usuario BIGINT;
  DECLARE v_total DECIMAL(12,2);
  DECLARE v_puntos INT DEFAULT 0;

  -- obtain user and total from pedido
  SELECT usuario_id, total INTO v_usuario, v_total
  FROM pedido
  WHERE id = p_pedido_id;

  IF v_usuario IS NULL THEN
    SET p_puntos_asignados = 0;
    SET p_msg = 'Pedido no encontrado';
    LEAVE proc_end;
  END IF;

  SET v_puntos = FLOOR(v_total / 10);

  -- update fidelizacion table (upsert behaviour)
  UPDATE fidelizacion SET puntos = puntos + v_puntos WHERE usuario_id = v_usuario;
  IF ROW_COUNT() = 0 THEN
    INSERT INTO fidelizacion (usuario_id, puntos) VALUES (v_usuario, v_puntos);
  END IF;

  -- insert history
  INSERT INTO puntos_historial (usuario_id, pedido_id, puntos, motivo)
    VALUES (v_usuario, p_pedido_id, v_puntos, CONCAT('Puntos por pedido #', p_pedido_id));

  SET p_puntos_asignados = v_puntos;
  SET p_msg = 'Puntos asignados correctamente';

  proc_end: BEGIN END;
END//
DELIMITER ;
