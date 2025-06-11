package cl.perfulandia.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PedidoDTO {
    private Long id;
    private Long ventaId;
    private Long clienteId;
    private String estado;
    private LocalDateTime fechaCreacion;
    private BigDecimal montoTotal;
    private List<DetallePedidoDTO> detalles;

}
