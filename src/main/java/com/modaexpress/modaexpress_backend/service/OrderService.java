package com.modaexpress.modaexpress_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modaexpress.modaexpress_backend.dto.OrderDto;
import com.modaexpress.modaexpress_backend.dto.OrderItemDto;
import com.modaexpress.modaexpress_backend.repository.JdbcOrderRepository;
import com.modaexpress.modaexpress_backend.dto.ApiResponse;

import java.util.Map;
import java.util.List;

@Service
public class OrderService {

    private final JdbcOrderRepository repo;

    public OrderService(JdbcOrderRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Map<String,Object> createOrder(int usuarioId, String metodoPago, List<OrderItemDto> detalles) {
        try {
            // convert detalles to JSON string expected by the SP
            // simple construction (frontend should pass correct structure); we'll build a JSON array
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i=0;i<detalles.size();i++){
                OrderItemDto it = detalles.get(i);
                sb.append('{')
                  .append("\"variante_id\":").append(it.getVarianteId()).append(',')
                  .append("\"cantidad\":").append(it.getCantidad()).append(',')
                  .append("\"precio_unitario\":").append(it.getPrecioUnitario())
                  .append('}');
                if (i<detalles.size()-1) sb.append(',');
            }
            sb.append(']');
            String detallesJson = sb.toString();
            Map<String,Object> out = repo.crearPedidoCompleto(usuarioId, metodoPago, detallesJson);
            return out;
        } catch (Exception e) {
            throw e;
        }
    }

    public OrderDto getOrder(Long id) {
        return repo.obtenerDetallePedido(id);
    }
}
