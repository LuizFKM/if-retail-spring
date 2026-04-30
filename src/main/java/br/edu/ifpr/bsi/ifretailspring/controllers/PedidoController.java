package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDetailDTO>> listarPedidos(){
        List<PedidoDetailDTO> pedidos = this.pedidoService.listar();
        return ResponseEntity.ok(pedidos);

    }

    @PostMapping
    public ResponseEntity<PedidoDetailDTO> criar(@RequestBody PedidoRequestDTO pedido){
        PedidoDetailDTO pedidoSalvo = pedidoService.salvar(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }



}
