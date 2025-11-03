package com.modaexpress.modaexpress_backend.exceptions;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.model.mongo.SystemLog;
import com.modaexpress.modaexpress_backend.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogService logService;

    public GlobalExceptionHandler(LogService logService) {
        this.logService = logService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex, WebRequest request) {
        SystemLog log = new SystemLog();
        log.setLevel("ERROR");
        log.setService(request.getDescription(false));
        try { log.setEndpoint(request.getDescription(false)); } catch (Exception e) { /* ignore */ }
        log.setMessage(ex.getMessage());
        log.setStacktrace(Arrays.toString(ex.getStackTrace()));
        log.setTimestamp(LocalDateTime.now());
        try { logService.saveLog(log); } catch (Exception e) { /* swallow to avoid cascading errors */ }

        ApiResponse<Object> resp = new ApiResponse<>("error", null, "Ocurrió un error en el servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
    }
}
