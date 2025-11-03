package com.modaexpress.modaexpress_backend.repository;

import com.modaexpress.modaexpress_backend.model.mysql.AuditPedido;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAuditRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AuditPedido> findRecent(int limit) {
        String sql = "SELECT id, pedido_id AS pedidoId, old_status AS oldStatus, new_status AS newStatus, changed_by AS changedBy, changed_at AS changedAt "
                + "FROM audit_pedido ORDER BY changed_at DESC LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{limit}, new BeanPropertyRowMapper<>(AuditPedido.class));
    }
}
