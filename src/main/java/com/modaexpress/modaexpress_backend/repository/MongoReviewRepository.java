package com.modaexpress.modaexpress_backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.modaexpress.modaexpress_backend.model.mongo.Review;

@Repository
public interface MongoReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);
}
