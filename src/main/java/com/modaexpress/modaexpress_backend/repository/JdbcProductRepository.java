package com.modaexpress.modaexpress_backend.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.modaexpress.modaexpress_backend.dto.ProductDto;


@Repository
public class JdbcProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall spObtenerProductosActivos;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.spObtenerProductosActivos = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_obtener_productos_activos")
                .returningResultSet("rs", (rs, rowNum) -> {
                    ProductDto p = new ProductDto();
                    p.setId(rs.getLong("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setImagenUrl(rs.getString("imagen_url"));
                    return p;
                });
    }

    @SuppressWarnings("unchecked")
    public List<ProductDto> obtenerProductosActivos() {
        Map<String, Object> result = spObtenerProductosActivos.execute(new HashMap<>());
        Object rs = result.get("rs");
        if (rs == null) {
            return List.of();
        }
        return ((List<ProductDto>) rs).stream().collect(Collectors.toList());
    }
}
