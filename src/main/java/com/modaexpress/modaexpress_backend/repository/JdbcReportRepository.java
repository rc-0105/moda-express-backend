package com.modaexpress.modaexpress_backend.repository;

import com.modaexpress.modaexpress_backend.dto.TopProductDto;
import com.modaexpress.modaexpress_backend.dto.SalesMetrics;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SalesMetrics getSalesMetrics(LocalDate start, LocalDate end) {
        String totalsSql = "SELECT IFNULL(SUM(total),0) AS total_ventas, COUNT(*) AS total_pedidos, IFNULL(AVG(total),0) AS avg_ticket FROM pedido WHERE DATE(created_at) BETWEEN ? AND ?";
        Map<String,Object> totals = jdbcTemplate.queryForMap(totalsSql, start, end);

        BigDecimal totalVentas = totals.get("total_ventas")!=null? (BigDecimal)totals.get("total_ventas") : BigDecimal.ZERO;
        Long totalPedidos = totals.get("total_pedidos")!=null? ((Number)totals.get("total_pedidos")).longValue() : 0L;
        Double avgTicket = totals.get("avg_ticket")!=null? ((Number)totals.get("avg_ticket")).doubleValue() : 0.0;

        String topSql = "SELECT prod.id AS idProducto, prod.nombre AS nombre, SUM(dp.cantidad) AS cantidad_vendida "
                + "FROM detalle_pedido dp "
                + "JOIN variante_producto vp ON dp.variante_id = vp.id "
                + "JOIN producto prod ON vp.producto_id = prod.id "
                + "JOIN pedido ped ON dp.pedido_id = ped.id "
                + "WHERE DATE(ped.created_at) BETWEEN ? AND ? "
                + "GROUP BY prod.id, prod.nombre ORDER BY cantidad_vendida DESC LIMIT 5";

        List<TopProductDto> top = new ArrayList<>();
        jdbcTemplate.query(topSql, new Object[]{start, end}, (ResultSet rs) -> {
            while (rs.next()) {
                TopProductDto t = new TopProductDto(rs.getLong("idProducto"), rs.getString("nombre"), rs.getLong("cantidad_vendida"));
                top.add(t);
            }
            return null;
        });

        SalesMetrics m = new SalesMetrics(totalVentas, totalPedidos, avgTicket, top);
        return m;
    }
}
