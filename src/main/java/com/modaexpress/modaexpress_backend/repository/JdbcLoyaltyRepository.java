package com.modaexpress.modaexpress_backend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcLoyaltyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall assignPointsCall;

    public JdbcLoyaltyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.assignPointsCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_asignar_puntos_fidelizacion")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("p_pedido_id", Types.BIGINT),
                        new SqlOutParameter("p_puntos_asignados", Types.INTEGER),
                        new SqlOutParameter("p_msg", Types.VARCHAR)
                );
    }

    public Map<String, Object> assignPoints(Long pedidoId) {
        Map<String, Object> in = new HashMap<>();
        in.put("p_pedido_id", pedidoId);
        return assignPointsCall.execute(in);
    }
}
