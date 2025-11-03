package com.modaexpress.modaexpress_backend.controller;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.model.mongo.UserBehavior;
import com.modaexpress.modaexpress_backend.service.BehaviorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/behavior")
public class BehaviorController {

    private final BehaviorService service;

    public BehaviorController(BehaviorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserBehavior>> logBehavior(@RequestBody UserBehavior behavior) {
        UserBehavior saved = service.saveBehavior(behavior);
        return ResponseEntity.ok(new ApiResponse<>("ok", saved, null));
    }
}
