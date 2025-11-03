package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.model.mysql.AuditPedido;
import com.modaexpress.modaexpress_backend.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditService service;

    public AuditController(AuditService service) {
        this.service = service;
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<AuditPedido>>> getAudits(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(new ApiResponse<>("ok", service.getRecentAudits(limit), null));
    }
}
