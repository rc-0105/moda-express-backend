package com.modaexpress.modaexpress_backend.dto;

public class ReviewStatsDto {
    private double avgRating;
    private int count;

    public ReviewStatsDto() {}
    public ReviewStatsDto(double avgRating, int count) { this.avgRating = avgRating; this.count = count; }
    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
