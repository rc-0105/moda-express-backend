package com.modaexpress.modaexpress_backend.dto;

public class LoyaltyResult {
    private int points;
    private String message;

    public LoyaltyResult() {}
    public LoyaltyResult(int points, String message) {
        this.points = points;
        this.message = message;
    }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
