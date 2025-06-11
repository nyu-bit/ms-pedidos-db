package cl.perfulandia.model.entities;

import java.math.BigDecimal;

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
@Table(name = "detalle_pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;
    
    @Column(name = "producto_id", nullable = false)
    private Long productoId;
    
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;
    
    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;

    
}
