package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.dto.LoyaltyResult;
import com.modaexpress.modaexpress_backend.service.LoyaltyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {

    private final LoyaltyService service;

    public LoyaltyController(LoyaltyService service) {
        this.service = service;
    }

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Map<String,Object>>> apply(@RequestBody Map<String, Object> body) {
        Object pid = body.get("pedido_id");
        Long pedidoId = null;
        if (pid instanceof Number) pedidoId = ((Number)pid).longValue();
        if (pedidoId == null) return ResponseEntity.badRequest().body(new ApiResponse<>("error", null, "pedido_id is required"));

        LoyaltyResult res = service.applyPointsToOrder(pedidoId);
        Map<String,Object> data = Map.of("puntos", res.getPoints(), "msg", res.getMessage());
        return ResponseEntity.ok(new ApiResponse<>("ok", data, null));
    }
}
