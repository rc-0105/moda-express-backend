-- V3: insert sample data for testing
INSERT INTO producto (nombre, precio, descripcion, categoria_id, imagen_url, activo) VALUES
('Camisa Básica', 19.99, 'Camisa de algodón', NULL, NULL, TRUE),
('Pantalón Denim', 39.99, 'Pantalón azul', NULL, NULL, TRUE);

-- Insert variants for first product
INSERT INTO variante_producto (producto_id, talla, color, stock, sku) VALUES
(1, 'M', 'Negro', 10, 'CAM001-M-BL'),
(1, 'L', 'Negro', 5, 'CAM001-L-BL');
