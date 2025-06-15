package cl.perfulandia.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.perfulandia.model.dto.PedidoDTO;
import cl.perfulandia.model.entities.Pedido;
import cl.perfulandia.model.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<PedidoDTO> findAll() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO findById(Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.map(this::toDTO).orElse(null);
    }

    public PedidoDTO save(PedidoDTO dto) {
        Pedido saved = pedidoRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public PedidoDTO update(Long id, PedidoDTO dto) {
        Pedido entity = toEntity(dto);
        entity.setId(id);
        return toDTO(pedidoRepository.save(entity));
    }

    public void delete(Long id) {
        pedidoRepository.deleteById(id);
    }

    // Convertir de entidad a DTO 
    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getClienteId());
        dto.setProductoId(pedido.getProductoId());
        dto.setCantidad(pedido.getCantidad());
        dto.setEstado(pedido.getEstado());
        return dto;
    }

    // Convertir de DTO a entidad 
    private Pedido toEntity(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setId(dto.getId());
        pedido.setClienteId(dto.getClienteId());
        pedido.setProductoId(dto.getProductoId());
        pedido.setCantidad(dto.getCantidad());
        pedido.setEstado(dto.getEstado());
        return pedido;
    }
}