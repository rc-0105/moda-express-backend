package com.modaexpress.modaexpress_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;


@RestController
@RequestMapping("/api")
public class HealthController {

    private final JdbcTemplate jdbc;
    private final MongoTemplate mongo;

    public HealthController(JdbcTemplate jdbc, MongoTemplate mongo) {
        this.jdbc = jdbc;
        this.mongo = mongo;
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> out = new HashMap<>();
        try {
            jdbc.queryForObject("SELECT 1", Integer.class);
            out.put("mysql", true);
        } catch (Exception e) {
            out.put("mysql", false);
        }

        try {
            mongo.executeCommand("{ ping: 1 }");
            out.put("mongo", true);
        } catch (Exception e) {
            out.put("mongo", false);
        }

        ApiResponse<Map<String, Object>> resp = new ApiResponse<>("ok", out, null);
        return ResponseEntity.ok(resp);
    }
}
