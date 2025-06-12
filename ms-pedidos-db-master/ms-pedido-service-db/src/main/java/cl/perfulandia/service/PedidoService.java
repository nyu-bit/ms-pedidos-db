package cl.perfulandia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PedidoDTO findPedidoById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        if (pedido.isPresent()) {
            PedidoDTO pedidoDto = translateEntityToDto(pedido.get());
            List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(id);
            pedidoDto.setDetalles(translateDetallesToDto(detalles));
            return pedidoDto;
        }
        return null;
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

    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(pedidoDTO.getClienteId());
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setFechaCreacion(pedidoDTO.getFechaCreacion());
        pedido.setMontoTotal(pedidoDTO.getMontoTotal());
        Pedido savedPedido = pedidoRepository.save(pedido);

        if (pedidoDTO.getDetalles() != null) {
            for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetalles()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setPedidoId(savedPedido.getId());
                detalle.setProductoId(detalleDTO.getProductoId());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
                detalle.setSubtotal(detalleDTO.getSubtotal());
                detallePedidoRepository.save(detalle);
            }
        }

        return findPedidoById(savedPedido.getId());
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