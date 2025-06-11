package cl.perfulandia.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.perfulandia.model.entities.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoId(Long pedidoId);
    void deleteByPedidoId(Long pedidoId);
}