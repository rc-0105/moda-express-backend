package com.modaexpress.modaexpress_backend.dto;

public class OrderItemDto {
    private Long varianteId;
    private Integer cantidad;
    private Double precioUnitario;

    public OrderItemDto() {}

    public Long getVarianteId() { return varianteId; }
    public void setVarianteId(Long varianteId) { this.varianteId = varianteId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }
}
