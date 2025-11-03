package com.modaexpress.modaexpress_backend.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.ObjectProvider;

@Service
public class HealthService {

    private final JdbcTemplate jdbcTemplate;
    private final MongoTemplate mongoTemplate;

    public HealthService(JdbcTemplate jdbcTemplate, ObjectProvider<MongoTemplate> mongoTemplateProvider) {
        this.jdbcTemplate = jdbcTemplate;
        this.mongoTemplate = mongoTemplateProvider.getIfAvailable();
    }

    public boolean checkMySql() {
        try {
            Integer v = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return v != null && v == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkMongo() {
        if (this.mongoTemplate == null) return false;
        try {
            mongoTemplate.executeCommand("{ ping: 1 }");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
