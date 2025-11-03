package com.modaexpress.modaexpress_backend.dto;

public class TopProductDto {
    private Long idProducto;
    private String nombre;
    private Long cantidadVendida;

    public TopProductDto() {}
    public TopProductDto(Long idProducto, String nombre, Long cantidadVendida) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
    }
    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(Long cantidadVendida) { this.cantidadVendida = cantidadVendida; }
}
