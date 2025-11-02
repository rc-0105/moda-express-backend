package com.modaexpress.modaexpress_backend.dto;

public class ApiResponse<T> {
    private String status;
    private T data;
    private Object error;

    public ApiResponse() {}
    public ApiResponse(String status, T data, Object error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }
    // getters / setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public Object getError() { return error; }
    public void setError(Object error) { this.error = error; }
}
