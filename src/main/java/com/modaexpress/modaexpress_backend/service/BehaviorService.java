package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.model.mongo.UserBehavior;
import com.modaexpress.modaexpress_backend.repository.MongoBehaviorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BehaviorService {

    private final MongoBehaviorRepository repo;

    public BehaviorService(MongoBehaviorRepository repo) {
        this.repo = repo;
    }

    public UserBehavior saveBehavior(UserBehavior behavior) {
        if (behavior.getCreatedAt() == null) behavior.setCreatedAt(LocalDateTime.now());
        return repo.save(behavior);
    }
}
