package com.modaexpress.modaexpress_backend.service;

import com.modaexpress.modaexpress_backend.model.mongo.Review;
import com.modaexpress.modaexpress_backend.repository.MongoReviewRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private final MongoReviewRepository repo;

    public ReviewService(MongoReviewRepository repo) {
        this.repo = repo;
    }

    public List<Review> getApprovedByProduct(String productId) {
        return repo.findByProductIdAndMetadataModerationStatus(productId, "approved");
    }

    public Review saveReview(Review review) {
        if (review.getCreatedAt() == null) review.setCreatedAt(LocalDateTime.now());
        if (review.getMetadata() == null) review.setMetadata(new Review.Metadata());
        if (review.getMetadata().getModerationStatus() == null) review.getMetadata().setModerationStatus("pending");
        return repo.save(review);
    }

    public Review moderateReview(String id, String newStatus) {
        Review review = repo.findById(id).orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        review.getMetadata().setModerationStatus(newStatus);
        return repo.save(review);
    }

    public Map<String, Object> getStats(String productId) {
        List<Document> docs = repo.aggregateStatsByProductId(productId);
        if (docs == null || docs.isEmpty()) return Map.of("avgRating", 0, "count", 0);
        Document d = docs.get(0);
        Object avg = d.get("avgRating");
        Object count = d.get("count");
        double avgRating = avg instanceof Number ? ((Number)avg).doubleValue() : 0.0;
        int cnt = count instanceof Number ? ((Number)count).intValue() : 0;
        return Map.of("avgRating", avgRating, "count", cnt);
    }
}
