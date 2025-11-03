-- V11: trigger to log status changes on pedido
DROP TRIGGER IF EXISTS tr_after_update_pedido;
DELIMITER //
CREATE TRIGGER tr_after_update_pedido
AFTER UPDATE ON pedido
FOR EACH ROW
BEGIN
  IF (OLD.estado IS NULL AND NEW.estado IS NOT NULL) OR (OLD.estado IS NOT NULL AND OLD.estado <> NEW.estado) THEN
    INSERT INTO audit_pedido (pedido_id, old_status, new_status, changed_by)
    VALUES (OLD.id, OLD.estado, NEW.estado, 'system');
  END IF;
END//
DELIMITER ;
