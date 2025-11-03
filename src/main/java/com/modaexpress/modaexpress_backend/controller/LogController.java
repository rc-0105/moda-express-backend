package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.model.mongo.SystemLog;
import com.modaexpress.modaexpress_backend.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService service;

    public LogController(LogService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SystemLog>>> getLogs(@RequestParam(defaultValue = "ERROR") String level) {
        return ResponseEntity.ok(new ApiResponse<>("ok", service.getLogs(level), null));
    }
}
