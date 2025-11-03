-- V6: helper function to calculate points (1 point per 10 currency units)
DROP FUNCTION IF EXISTS fn_calcular_puntos;
DELIMITER //
CREATE FUNCTION fn_calcular_puntos(p_monto DECIMAL(12,2)) RETURNS INT
DETERMINISTIC
BEGIN
  RETURN FLOOR(p_monto / 10);
END//
DELIMITER ;
