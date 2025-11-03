package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.model.mongo.Review;
import com.modaexpress.modaexpress_backend.service.ReviewService;
import com.modaexpress.modaexpress_backend.dto.ReviewRequestDto;
import com.modaexpress.modaexpress_backend.dto.ReviewDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<Review>>> getByProduct(@PathVariable String productId) {
        List<Review> list = service.getApprovedByProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>("ok", list, null));
    }

    @GetMapping("/stats/{productId}")
    public ResponseEntity<ApiResponse<Map<String,Object>>> getStats(@PathVariable String productId) {
        Map<String,Object> stats = service.getStats(productId);
        return ResponseEntity.ok(new ApiResponse<>("ok", stats, null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDto>> create(@Valid @RequestBody ReviewRequestDto req) {
        // map DTO -> Review
        Review r = new Review();
        r.setUserId(req.getUserId());
        r.setProductId(req.getProductId());
        r.setRating(req.getRating()!=null?req.getRating():0.0);
        r.setComment(req.getComment());
        if (req.getMetadata() != null) {
            Review.Metadata md = new Review.Metadata();
            md.setHelpfulVotes(req.getMetadata().getHelpfulVotes()!=null?req.getMetadata().getHelpfulVotes():0);
            md.setModerationStatus(req.getMetadata().getModerationStatus());
            md.setSentimentScore(req.getMetadata().getSentimentScore()!=null?req.getMetadata().getSentimentScore():0.0);
            r.setMetadata(md);
        }
        Review saved = service.saveReview(r);
        ReviewDto respDto = new ReviewDto(saved);
        return ResponseEntity.ok(new ApiResponse<>("ok", respDto, null));
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<ApiResponse<Review>> moderate(@PathVariable String id, @RequestParam String status) {
        Review updated = service.moderateReview(id, status);
        return ResponseEntity.ok(new ApiResponse<>("ok", updated, null));
    }
}
