package br.edu.ifpr.bsi.ifretailspring.controllers;

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
    public ResponseEntity<List<PedidoDetailDTO>> listarPedidos() {
        return ResponseEntity.ok(this.pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetailDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.pedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDetailDTO> criar(@RequestBody PedidoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.pedidoService.salvar(request));
    }

    // PATCH /pedidos/{id}/cancelar — seta status=false sem deletar o registro
    @PatchMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        this.pedidoService.cancelar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.pedidoService.excluir(id);
    }
}
