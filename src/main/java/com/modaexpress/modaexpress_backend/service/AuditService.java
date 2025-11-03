package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.model.mysql.AuditPedido;
import com.modaexpress.modaexpress_backend.repository.JdbcAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    private final JdbcAuditRepository repo;

    public AuditService(JdbcAuditRepository repo) {
        this.repo = repo;
    }

    public List<AuditPedido> getRecentAudits(int limit) {
        return repo.findRecent(limit);
    }
}
