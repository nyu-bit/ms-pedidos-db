package cl.perfulandia.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventario-service", url = "${inventario.service.url}")
public interface InventarioClient {
    @GetMapping("/api/inventario/stock/{productId}")
    StockResponse verificarStock(@PathVariable Long productId);
    
    @PostMapping("/api/inventario/reservar")
    ReservaResponse reservarStock(@RequestBody ReservaRequest request);
}

// Response DTOs para Inventario
class StockResponse {
    private Long productoId;
    private Integer disponible;
    private String ubicacion;
    
    // Getters y Setters
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getDisponible() { return disponible; }
    public void setDisponible(Integer disponible) { this.disponible = disponible; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}

class ReservaResponse {
    private String estado;
    private String mensaje;
    
    // Getters y Setters
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

class ReservaRequest {
    private Long productoId;
    private Integer cantidad;
    
    // Constructor
    public ReservaRequest(Long productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}