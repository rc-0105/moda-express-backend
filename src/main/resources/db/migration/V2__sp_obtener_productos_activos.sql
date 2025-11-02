DELIMITER //

CREATE PROCEDURE sp_obtener_productos_activos()
BEGIN
  SELECT
    p.id,
    p.nombre,
    p.precio,
    p.descripcion,
    p.imagen_url
  FROM producto p
  WHERE p.activo = TRUE;
END //

DELIMITER ;
