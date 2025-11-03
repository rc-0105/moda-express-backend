package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.dto.OrderDto;
import com.modaexpress.modaexpress_backend.dto.OrderItemDto;
import com.modaexpress.modaexpress_backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createOrder(@RequestBody Map<String,Object> body) {
        try {
            // accept either snake_case or camelCase for incoming JSON keys
            Number usuarioNum = (Number) (body.getOrDefault("usuario_id", body.get("usuarioId")));
            String metodoPago = (String) (body.getOrDefault("metodo_pago", body.get("metodoPago")));
            @SuppressWarnings("unchecked")
            List<Map<String,Object>> detalles = (List<Map<String,Object>>) (body.getOrDefault("detalles", body.get("items")));

            if (detalles == null) {
                ApiResponse<Object> err = new ApiResponse<>("error", null, "'detalles' (items) is required in request body");
                return ResponseEntity.badRequest().body(err);
            }

            if (usuarioNum == null) {
                ApiResponse<Object> err = new ApiResponse<>("error", null, "'usuario_id' is required in request body");
                return ResponseEntity.badRequest().body(err);
            }

            if (metodoPago == null) {
                ApiResponse<Object> err = new ApiResponse<>("error", null, "'metodo_pago' is required in request body");
                return ResponseEntity.badRequest().body(err);
            }

            List<OrderItemDto> items = new java.util.ArrayList<>();
            for (Map<String,Object> m: detalles) {
                OrderItemDto it = new OrderItemDto();
                Number vid = (Number) (m.getOrDefault("variante_id", m.get("varianteId")));
                Number cant = (Number) (m.getOrDefault("cantidad", m.get("cantidad")));
                Number pu = (Number) (m.getOrDefault("precio_unitario", m.get("precioUnitario")));
                if (vid == null || cant == null || pu == null) {
                        ApiResponse<Object> err = new ApiResponse<>("error", null, "Each item must contain variante_id, cantidad and precio_unitario");
                        return ResponseEntity.badRequest().body(err);
                }
                it.setVarianteId(vid.longValue());
                it.setCantidad(cant.intValue());
                it.setPrecioUnitario(pu.doubleValue());
                items.add(it);
            }

            Integer usuarioId = usuarioNum == null ? null : usuarioNum.intValue();
            Map<String,Object> out = service.createOrder(usuarioId, metodoPago, items);
            ApiResponse<Object> resp = new ApiResponse<>("ok", out, null);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            // Log full stacktrace for diagnosis (e.g. JDBC connection failures)
            log.error("Error creating order", e);
            ApiResponse<Object> err = new ApiResponse<>("error", null, e.getMessage());
            return ResponseEntity.status(500).body(err);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getOrder(@PathVariable("id") Long id) {
        try {
            OrderDto dto = service.getOrder(id);
            if (dto == null) return ResponseEntity.status(404).body(new ApiResponse<Object>("error", null, "Not found"));
            return ResponseEntity.ok(new ApiResponse<Object>("ok", dto, null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<Object>("error", null, e.getMessage()));
        }
    }
}
