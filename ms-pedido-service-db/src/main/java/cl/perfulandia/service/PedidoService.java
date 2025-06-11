package cl.perfulandia.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.perfulandia.clients.InventarioClient;
import cl.perfulandia.clients.VentasClient;
import cl.perfulandia.model.dto.DetallePedidoDTO;
import cl.perfulandia.model.dto.PedidoDTO;
import cl.perfulandia.model.entities.DetallePedido;
import cl.perfulandia.model.entities.Pedido;
import cl.perfulandia.model.repositories.DetallePedidoRepository;
import cl.perfulandia.model.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    VentasClient ventasClient;

    @Autowired
    InventarioClient inventarioClient;

    public PedidoDTO findPedidoById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        PedidoDTO pedidoDto = null;

        if (pedido.isPresent()) {
            pedidoDto = translateEntityToDto(pedido.get());
            // Agregar detalles
            List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(id);
            pedidoDto.setDetalles(translateDetallesToDto(detalles));
        }

        return pedidoDto;
    }

    public List<PedidoDTO> findPedidosByCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        List<PedidoDTO> pedidosDto = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            PedidoDTO dto = translateEntityToDto(pedido);
            List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(pedido.getId());
            dto.setDetalles(translateDetallesToDto(detalles));
            pedidosDto.add(dto);
        }

        return pedidosDto;
    }

    public PedidoDTO crearPedidoDesdeVenta(Long ventaId) {
        try {
            // 1. Obtener datos de la venta
            VentaResponse venta = ventasClient.getVenta(ventaId);
            List<ProductoVentaResponse> productos = ventasClient.getProductosVenta(ventaId);

            // 2. Verificar stock para cada producto
            for (ProductoVentaResponse producto : productos) {
                StockResponse stock = inventarioClient.verificarStock(producto.getProductoId());
                if (stock.getDisponible() < producto.getCantidad()) {
                    return null; // Stock insuficiente
                }
            }

            // 3. Crear pedido
            Pedido pedido = new Pedido();
            pedido.setVentaId(ventaId);
            pedido.setClienteId(venta.getClienteId());
            pedido.setEstado("CONFIRMADO");
            pedido.setFechaCreacion(LocalDateTime.now());
            pedido.setMontoTotal(venta.getTotal());

            Pedido savedPedido = pedidoRepository.save(pedido);

            // 4. Crear detalles del pedido // Poner Apis que se usan en el servicio de ventas
            for (ProductoVentaResponse producto : productos) {
                DetallePedido detalle = new DetallePedido();
                detalle.setPedidoId(savedPedido.getId());
                detalle.setProductoId(producto.getProductoId());
                detalle.setCantidad(producto.getCantidad());
                detalle.setPrecioUnitario(producto.getPrecio());
                detalle.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(producto.getCantidad())));

                detallePedidoRepository.save(detalle);

                // 5. Reservar stock en inventario
                ReservaRequest reserva = new ReservaRequest(producto.getProductoId(), producto.getCantidad());
                inventarioClient.reservarStock(reserva);
            }

            return translateEntityToDto(savedPedido);

        } catch (Exception e) {
            return null;
        }
    }

    public PedidoDTO translateEntityToDto(Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setVentaId(pedido.getVentaId());
        pedidoDTO.setClienteId(pedido.getClienteId());
        pedidoDTO.setEstado(pedido.getEstado());
        pedidoDTO.setFechaCreacion(pedido.getFechaCreacion());
        pedidoDTO.setMontoTotal(pedido.getMontoTotal());
        return pedidoDTO;
    }

    public List<DetallePedidoDTO> translateDetallesToDto(List<DetallePedido> detalles) {
        List<DetallePedidoDTO> detallesDto = new ArrayList<>();
        for (DetallePedido detalle : detalles) {
            DetallePedidoDTO detalleDto = new DetallePedidoDTO();
            detalleDto.setProductoId(detalle.getProductoId());
            detalleDto.setCantidad(detalle.getCantidad());
            detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
            detalleDto.setSubtotal(detalle.getSubtotal());
            detallesDto.add(detalleDto);
        }
        return detallesDto;
    }
}