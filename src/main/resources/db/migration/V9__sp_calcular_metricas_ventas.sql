-- V9: calculate sales metrics for a date range (totals + top products)
DROP PROCEDURE IF EXISTS sp_calcular_metricas_ventas;
DELIMITER //
CREATE PROCEDURE sp_calcular_metricas_ventas(
  IN p_fecha_inicio DATE,
  IN p_fecha_fin DATE
)
BEGIN
  -- totals
  SELECT 
    IFNULL(SUM(total),0) AS total_ventas,
    COUNT(*) AS total_pedidos,
    IFNULL(AVG(total),0) AS avg_ticket
  FROM pedido
  WHERE DATE(created_at) BETWEEN p_fecha_inicio AND p_fecha_fin;

  -- top 5 productos by quantity sold
  SELECT prod.id AS idProducto, prod.nombre AS nombre, SUM(dp.cantidad) AS cantidad_vendida
  FROM detalle_pedido dp
  JOIN variante_producto vp ON dp.variante_id = vp.id
  JOIN producto prod ON vp.producto_id = prod.id
  JOIN pedido ped ON dp.pedido_id = ped.id
  WHERE DATE(ped.created_at) BETWEEN p_fecha_inicio AND p_fecha_fin
  GROUP BY prod.id, prod.nombre
  ORDER BY cantidad_vendida DESC
  LIMIT 5;
END//
DELIMITER ;
