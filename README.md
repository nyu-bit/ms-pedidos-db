CREATE DATABASE pedidos;
USE pedidos;

CREATE TABLE pedido (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cliente_id BIGINT NOT NULL,
  producto_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  estado VARCHAR(20) NOT NULL
);

-- Datos para  prueba
INSERT INTO pedido (cliente_id, producto_id, cantidad, estado) VALUES
(1, 1001, 2, 'CREADO'),
(2, 1002, 1, 'CREADO');
