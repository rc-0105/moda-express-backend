package com.modaexpress.modaexpress_backend.dto;

import java.util.List;
import java.util.Date;

public class OrderDto {
    private Long pedidoId;
    private Long usuarioId;
    private String estado;
    private Double total;
    private Date createdAt;
    private List<OrderItemDto> detalles;

    public OrderDto() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public List<OrderItemDto> getDetalles() { return detalles; }
    public void setDetalles(List<OrderItemDto> detalles) { this.detalles = detalles; }
}
