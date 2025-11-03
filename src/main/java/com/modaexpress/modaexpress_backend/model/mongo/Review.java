package com.modaexpress.modaexpress_backend.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "resenas")
public class Review {
    @Id
    private String id;
    private String userId;
    private String productId;
    private double rating;
    private String comment;
    private Metadata metadata;
    private LocalDateTime createdAt;

    public Review() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public Metadata getMetadata() { return metadata; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class Metadata {
        private int helpfulVotes;
        private String moderationStatus;
        private double sentimentScore;

        public Metadata() {}
        public int getHelpfulVotes() { return helpfulVotes; }
        public void setHelpfulVotes(int helpfulVotes) { this.helpfulVotes = helpfulVotes; }
        public String getModerationStatus() { return moderationStatus; }
        public void setModerationStatus(String moderationStatus) { this.moderationStatus = moderationStatus; }
        public double getSentimentScore() { return sentimentScore; }
        public void setSentimentScore(double sentimentScore) { this.sentimentScore = sentimentScore; }
    }
}