package com.modaexpress.modaexpress_backend.repository;

import com.modaexpress.modaexpress_backend.model.mongo.Review;
import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductIdAndMetadataModerationStatus(String productId, String status);
    List<Review> findByUserId(String userId);
    List<Review> findByProductId(String productId);

    @Aggregation(pipeline = {
      "{ $match: { productId: ?0, 'metadata.moderationStatus': 'approved' } }",
      "{ $group: { _id: '$productId', avgRating: { $avg: '$rating' }, count: { $sum: 1 } } }"
    })
    List<Document> aggregateStatsByProductId(String productId);
}
