CREATE DATABASE pedido_db;

use pedido_db;

CREATE TABLE pedidos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    venta_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME,
    monto_total DECIMAL(10,2),
    PRIMARY KEY (id)
);
CREATE TABLE detalle_pedidos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    PRIMARY KEY (id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT INTO pedidos (venta_id, cliente_id, estado, fecha_creacion, fecha_actualizacion, monto_total)
VALUES (101, 501, 'CONFIRMADO', '2025-06-11 18:00:00', NULL, 77000.00);

INSERT INTO pedidos (venta_id, cliente_id, estado, fecha_creacion, fecha_actualizacion, monto_total)
VALUES (102, 502, 'PENDIENTE', '2025-06-11 19:00:00', NULL, 42000.00);

INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad, precio_unitario, subtotal)
VALUES (1, 1, 1, 35000.00, 35000.00);

INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad, precio_unitario, subtotal)
VALUES (1, 2, 1, 42000.00, 42000.00);

select * from detalle_pedidos;
select * from pedidos;
