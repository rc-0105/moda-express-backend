package com.modaexpress.modaexpress_backend.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesMetrics {
    private BigDecimal totalVentas;
    private Long totalPedidos;
    private Double avgTicket;
    private List<TopProductDto> topProducts;

    public SalesMetrics() {}
    public SalesMetrics(BigDecimal totalVentas, Long totalPedidos, Double avgTicket, List<TopProductDto> topProducts) {
        this.totalVentas = totalVentas;
        this.totalPedidos = totalPedidos;
        this.avgTicket = avgTicket;
        this.topProducts = topProducts;
    }
    public BigDecimal getTotalVentas() { return totalVentas; }
    public void setTotalVentas(BigDecimal totalVentas) { this.totalVentas = totalVentas; }
    public Long getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(Long totalPedidos) { this.totalPedidos = totalPedidos; }
    public Double getAvgTicket() { return avgTicket; }
    public void setAvgTicket(Double avgTicket) { this.avgTicket = avgTicket; }
    public List<TopProductDto> getTopProducts() { return topProducts; }
    public void setTopProducts(List<TopProductDto> topProducts) { this.topProducts = topProducts; }
}
