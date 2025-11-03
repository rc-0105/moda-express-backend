package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.dto.SalesMetrics;
import com.modaexpress.modaexpress_backend.repository.JdbcReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportService {

    private final JdbcReportRepository repo;

    public ReportService(JdbcReportRepository repo) {
        this.repo = repo;
    }

    public SalesMetrics getSalesMetrics(LocalDate start, LocalDate end) {
        return repo.getSalesMetrics(start, end);
    }
}
