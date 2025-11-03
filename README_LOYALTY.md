# Programa de Fidelización y Métricas de Ventas

Este documento describe cómo funcionan los scripts y endpoints añadidos para fidelización y reportes.

Migraciones (colocar en `src/main/resources/db/migration`):
- V5__create_points_history.sql — crea `puntos_historial`.
- V6__fn_calcular_puntos.sql — función `fn_calcular_puntos`.
- V7__sp_asignar_puntos_fidelizacion.sql — procedimiento para asignar puntos por pedido.
- V8__vw_ventas_diarias.sql — vista con ventas agregadas por día.
- V9__sp_calcular_metricas_ventas.sql — procedimiento que devuelve totales y top 5 productos.

Endpoints añadidos:
- POST /api/loyalty/apply
  - Body: `{ "pedido_id": 123 }`
  - Llama a `sp_asignar_puntos_fidelizacion` y devuelve `{puntos, msg}`.

- GET /api/reports/sales?start=YYYY-MM-DD&end=YYYY-MM-DD
  - Devuelve `total_ventas`, `total_pedidos`, `avg_ticket`, `top_products`.

Cómo aplicar las migraciones manualmente (si Flyway no está activo):
```bash
mysql -u modaexpress -p -h 127.0.0.1 modaexpress < src/main/resources/db/migration/V5__create_points_history.sql
mysql -u modaexpress -p -h 127.0.0.1 modaexpress < src/main/resources/db/migration/V6__fn_calcular_puntos.sql
mysql -u modaexpress -p -h 127.0.0.1 modaexpress < src/main/resources/db/migration/V7__sp_asignar_puntos_fidelizacion.sql
mysql -u modaexpress -p -h 127.0.0.1 modaexpress < src/main/resources/db/migration/V8__vw_ventas_diarias.sql
mysql -u modaexpress -p -h 127.0.0.1 modaexpress < src/main/resources/db/migration/V9__sp_calcular_metricas_ventas.sql
```

Ejemplos curl:
```bash
# Aplicar puntos a pedido 15
curl -X POST http://localhost:8080/api/loyalty/apply -H "Content-Type: application/json" -d '{"pedido_id":15}'

# Obtener métricas
curl "http://localhost:8080/api/reports/sales?start=2025-01-01&end=2025-12-31"
```

Notas:
- Ajusté los nombres y columnas a las tablas ya existentes en el proyecto (`pedido`, `detalle_pedido`, `producto`, `variante_producto`).
- `puntos_historial` usa `usuario_id` en vez de FK a `Cliente` para evitar dependencias con tablas que no existen.
- El procedimiento de métricas devuelve DOS resultsets (totales + top products). En Java se usan dos queries separadas para simplicidad.
