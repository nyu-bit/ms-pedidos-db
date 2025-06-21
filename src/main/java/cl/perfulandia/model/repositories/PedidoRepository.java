package cl.perfulandia.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.perfulandia.model.entities.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}