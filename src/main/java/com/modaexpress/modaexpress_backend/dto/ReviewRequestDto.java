package com.modaexpress.modaexpress_backend.dto;

import jakarta.validation.constraints.*;
import java.util.Objects;

public class ReviewRequestDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String productId;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;

    @Size(max = 2000)
    private String comment;

    private MetadataDto metadata;

    public static class MetadataDto {
        @Min(0)
        private Integer helpfulVotes;
        private String moderationStatus;
        @DecimalMin(value = "-1.0")
        @DecimalMax(value = "1.0")
        private Double sentimentScore;

        public Integer getHelpfulVotes() { return helpfulVotes; }
        public void setHelpfulVotes(Integer helpfulVotes) { this.helpfulVotes = helpfulVotes; }
        public String getModerationStatus() { return moderationStatus; }
        public void setModerationStatus(String moderationStatus) { this.moderationStatus = moderationStatus; }
        public Double getSentimentScore() { return sentimentScore; }
        public void setSentimentScore(Double sentimentScore) { this.sentimentScore = sentimentScore; }
    }

    public ReviewRequestDto() {}
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public MetadataDto getMetadata() { return metadata; }
    public void setMetadata(MetadataDto metadata) { this.metadata = metadata; }

    @Override
    public boolean equals(Object o) { return Objects.equals(this, o); }
    @Override
    public int hashCode() { return Objects.hash(userId, productId, rating, comment); }
}
