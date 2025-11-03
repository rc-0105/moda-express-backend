-- V8: daily sales view
CREATE OR REPLACE VIEW vw_ventas_diarias AS
SELECT DATE(created_at) AS dia,
       COUNT(*) AS total_pedidos,
       SUM(total) AS total_ventas,
       AVG(total) AS avg_ticket
FROM pedido
GROUP BY DATE(created_at)
ORDER BY dia DESC;
