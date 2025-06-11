package cl.perfulandia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.perfulandia.model.dto.PedidoDTO;
import cl.perfulandia.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> findPedidoById(@PathVariable(name = "id") Long id) {
        PedidoDTO pedidoDTO = pedidoService.findPedidoById(id);
        return (pedidoDTO != null) ? new ResponseEntity<>(pedidoDTO, HttpStatus.OK) :
                                     new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoDTO>> findPedidosByCliente(@PathVariable(name = "clienteId") Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.findPedidosByCliente(clienteId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping("/crear-desde-venta/{ventaId}")
    public ResponseEntity<PedidoDTO> crearPedidoDesdeVenta(@PathVariable(name = "ventaId") Long ventaId) {
        PedidoDTO pedidoDTO = pedidoService.crearPedidoDesdeVenta(ventaId);
        return (pedidoDTO != null) ? new ResponseEntity<>(pedidoDTO, HttpStatus.CREATED) :
                                     new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAllPedidos() {
        // Implementar si es necesario
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}