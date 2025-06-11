package cl.perfulandia.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "venta_id", nullable = false)
    private Long ventaId;
    
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;
    
    @Column(name = "estado", nullable = false)
    private String estado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "monto_total", precision = 10, scale = 2)
    private BigDecimal montoTotal;

}
