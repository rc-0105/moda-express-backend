package com.modaexpress.modaexpress_backend.model.mysql;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
    private String imagenUrl;
}
