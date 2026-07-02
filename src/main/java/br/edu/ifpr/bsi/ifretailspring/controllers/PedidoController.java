package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "https://if-retail-frontend.onrender.com")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Page<PedidoDetailDTO>> listarPedidos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(this.pedidoService.listar(pageable));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<PedidoDetailDTO>> listarPorCliente(
            @PathVariable Long clienteId,
            @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(this.pedidoService.listarPorCliente(clienteId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetailDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.pedidoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoDetailDTO> criar(@RequestBody PedidoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.pedidoService.salvar(request));
    }

    @PatchMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        this.pedidoService.cancelar(id);
    }

    @PatchMapping("/{id}/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable Long id) {
        this.pedidoService.entregar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.pedidoService.excluir(id);
    }
}
