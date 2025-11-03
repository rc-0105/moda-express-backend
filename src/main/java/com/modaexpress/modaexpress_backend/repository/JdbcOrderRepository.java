package com.modaexpress.modaexpress_backend.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import java.sql.Types;
import org.springframework.stereotype.Repository;

import com.modaexpress.modaexpress_backend.dto.OrderDto;
import com.modaexpress.modaexpress_backend.dto.OrderItemDto;

@Repository
public class JdbcOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall spCrearPedido;
    private final SimpleJdbcCall spObtenerDetalle;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    // Declare parameters explicitly to avoid runtime metadata introspection issues
    this.spCrearPedido = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("sp_crear_pedido_completo")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(
            new SqlParameter("p_usuario_id", Types.BIGINT),
            new SqlParameter("p_metodo_pago", Types.VARCHAR),
            new SqlParameter("p_detalles_json", Types.VARCHAR),
            new SqlOutParameter("p_pedido_id", Types.BIGINT),
            new SqlOutParameter("p_estado", Types.VARCHAR)
        );

    this.spObtenerDetalle = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("sp_obtener_detalle_pedido")
        .withoutProcedureColumnMetaDataAccess()
        .declareParameters(new SqlParameter("p_pedido_id", Types.BIGINT));
    }

    public Map<String,Object> crearPedidoCompleto(int usuarioId, String metodoPago, String detallesJson) {
        Map<String,Object> in = new HashMap<>();
        in.put("p_usuario_id", usuarioId);
        in.put("p_metodo_pago", metodoPago);
        in.put("p_detalles_json", detallesJson);
        Map<String,Object> out = spCrearPedido.execute(in);
        return out;
    }

    @SuppressWarnings("unchecked")
    public OrderDto obtenerDetallePedido(Long pedidoId) {
        Map<String,Object> in = new HashMap<>();
        in.put("p_pedido_id", pedidoId);
        Map<String,Object> out = spObtenerDetalle.execute(in);
        // Expecting the stored procedure to return a resultset named 'rs' or the first result
        // We'll perform a direct query as fallback
        String sql = "SELECT p.id as pedido_id, p.usuario_id, p.total, p.estado, p.created_at, "
                + "dp.variante_id, dp.cantidad, dp.precio_unitario, pay.id as pago_id, pay.metodo as metodo_pago, pay.monto, sh.id as envio_id, sh.direccion, sh.estado as envio_estado "
                + "FROM pedido p "
                + "LEFT JOIN detalle_pedido dp ON dp.pedido_id = p.id "
                + "LEFT JOIN pago pay ON pay.pedido_id = p.id "
                + "LEFT JOIN envio sh ON sh.pedido_id = p.id "
                + "WHERE p.id = ?";

        List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, pedidoId);
        if (rows.isEmpty()) return null;

        OrderDto dto = new OrderDto();
        List<OrderItemDto> items = new ArrayList<>();
        for (Map<String,Object> r: rows) {
            if (dto.getPedidoId() == null) {
                dto.setPedidoId(((Number)r.get("pedido_id")).longValue());
                dto.setUsuarioId(r.get("usuario_id")!=null?((Number)r.get("usuario_id")).longValue():null);
                dto.setEstado((String)r.get("estado"));
                dto.setTotal(r.get("total")!=null?((Number)r.get("total")).doubleValue():null);
                dto.setCreatedAt(r.get("created_at")!=null?(java.util.Date)r.get("created_at"):null);
            }
            if (r.get("variante_id") != null) {
                OrderItemDto it = new OrderItemDto();
                it.setVarianteId(((Number)r.get("variante_id")).longValue());
                it.setCantidad(r.get("cantidad")!=null?((Number)r.get("cantidad")).intValue():0);
                it.setPrecioUnitario(r.get("precio_unitario")!=null?((Number)r.get("precio_unitario")).doubleValue():0.0);
                items.add(it);
            }
        }
        dto.setDetalles(items);
        return dto;
    }
}
