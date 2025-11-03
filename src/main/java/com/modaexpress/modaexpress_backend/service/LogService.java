package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.model.mongo.SystemLog;
import com.modaexpress.modaexpress_backend.repository.MongoLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    private final MongoLogRepository repo;

    public LogService(MongoLogRepository repo) {
        this.repo = repo;
    }

    public SystemLog saveLog(SystemLog log) {
        if (log.getTimestamp() == null) log.setTimestamp(LocalDateTime.now());
        return repo.save(log);
    }

    public List<SystemLog> getLogs(String level) {
        return repo.findByLevelOrderByTimestampDesc(level);
    }
}
