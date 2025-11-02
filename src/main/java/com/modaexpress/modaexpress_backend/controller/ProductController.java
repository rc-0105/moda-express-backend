package com.modaexpress.modaexpress_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modaexpress.modaexpress_backend.dto.ApiResponse;
import com.modaexpress.modaexpress_backend.dto.ProductDto;
import com.modaexpress.modaexpress_backend.service.ProductService;



@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getActiveProducts() {
        List<ProductDto> products = service.getActiveProducts();
        ApiResponse<List<ProductDto>> resp = new ApiResponse<>("ok", products, null);
        return ResponseEntity.ok(resp);
    }
}
