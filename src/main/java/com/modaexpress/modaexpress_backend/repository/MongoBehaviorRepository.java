package com.modaexpress.modaexpress_backend.repository;

import com.modaexpress.modaexpress_backend.model.mongo.UserBehavior;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoBehaviorRepository extends MongoRepository<UserBehavior, String> {
    List<UserBehavior> findByUserId(String userId);
}
