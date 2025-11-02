package com.modaexpress.modaexpress_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modaexpress.modaexpress_backend.dto.ProductDto;
import com.modaexpress.modaexpress_backend.repository.JdbcProductRepository;



@Service
public class ProductService {

    private final JdbcProductRepository jdbcProductRepository;

    public ProductService(JdbcProductRepository jdbcProductRepository) {
        this.jdbcProductRepository = jdbcProductRepository;
    }

    public List<ProductDto> getActiveProducts() {
        return jdbcProductRepository.obtenerProductosActivos();
    }
}
