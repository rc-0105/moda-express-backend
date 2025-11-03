package com.modaexpress.modaexpress_backend.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "user_behavior")
public class UserBehavior {
    @Id
    private String id;
    private String userId;
    private List<Map<String,Object>> pageViews;
    private List<String> searchQueries;
    private Map<String,Object> sessionData;
    private LocalDateTime createdAt;

    public UserBehavior() {}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<Map<String,Object>> getPageViews() { return pageViews; }
    public void setPageViews(List<Map<String,Object>> pageViews) { this.pageViews = pageViews; }
    public List<String> getSearchQueries() { return searchQueries; }
    public void setSearchQueries(List<String> searchQueries) { this.searchQueries = searchQueries; }
    public Map<String,Object> getSessionData() { return sessionData; }
    public void setSessionData(Map<String,Object> sessionData) { this.sessionData = sessionData; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
