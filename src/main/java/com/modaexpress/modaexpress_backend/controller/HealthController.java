package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.service.HealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        boolean mysql = healthService.checkMySql();
        boolean mongo = healthService.checkMongo();

        Map<String, Object> data = new HashMap<>();
        data.put("mysql", mysql);
        data.put("mongo", mongo);

        ApiResponse<Map<String, Object>> resp = new ApiResponse<>("ok", data, null);
        return ResponseEntity.ok(resp);
    }
}
