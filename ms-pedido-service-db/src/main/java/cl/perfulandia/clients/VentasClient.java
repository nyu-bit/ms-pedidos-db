package cl.perfulandia.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ventas-service", url = "${ventas.service.url}")
public interface VentasClient {
    @GetMapping("/api/ventas/{id}")
    VentaResponse getVenta(@PathVariable Long id);
    
    @GetMapping("/api/ventas/{id}/productos")
    List<ProductoVentaResponse> getProductosVenta(@PathVariable Long id);
}

// Response DTOs para Ventas
class VentaResponse {
    private Long id;
    private Long clienteId;
    private java.math.BigDecimal total;
    private String estado;
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public java.math.BigDecimal getTotal() { return total; }
    public void setTotal(java.math.BigDecimal total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

class ProductoVentaResponse {
    private Long productoId;
    private Integer cantidad;
    private java.math.BigDecimal precio;
    
    // Getters y Setters
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public java.math.BigDecimal getPrecio() { return precio; }
    public void setPrecio(java.math.BigDecimal precio) { this.precio = precio; }
}




