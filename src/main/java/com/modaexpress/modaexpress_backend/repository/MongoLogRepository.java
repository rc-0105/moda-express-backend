package com.modaexpress.modaexpress_backend.repository;

import com.modaexpress.modaexpress_backend.model.mongo.SystemLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoLogRepository extends MongoRepository<SystemLog, String> {
    List<SystemLog> findByLevelOrderByTimestampDesc(String level);
}
