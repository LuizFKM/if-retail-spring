package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "https://if-retail-frontend.onrender.com")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteDetailDTO>> listarClientes(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(this.clienteService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetailDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.clienteService.buscarPorId(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(this.clienteService.buscarPorCpf(cpf));
    }

    @GetMapping("/nome/{name}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorNome(@PathVariable String name) {
        return ResponseEntity.ok(this.clienteService.buscarPorNome(name));
    }

    @PostMapping
    public ResponseEntity<ClienteDetailDTO> criar(@RequestBody ClienteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.clienteService.salvar(request));
    }

    // multipart/form-data: campo "dados" (JSON) + campo "imagem" (arquivo)
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ClienteDetailDTO> atualizar(
            @PathVariable Long id,
            @RequestPart("dados") ClienteRequestDTO request,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {
        return ResponseEntity.ok(this.clienteService.atualizar(id, request, imagem));
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<ClienteDetailDTO> uploadFoto(
            @PathVariable Long id,
            @RequestParam("imagem") MultipartFile imagem) {
        return ResponseEntity.ok(this.clienteService.atualizarFoto(id, imagem));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.clienteService.excluir(id);
    }

    @PostMapping("/{id}/favoritos/{produtoId}")
    public ResponseEntity<ClienteDetailDTO> adicionarFavorito(
            @PathVariable Long id, @PathVariable Long produtoId) {
        return ResponseEntity.ok(this.clienteService.adicionarFavorito(id, produtoId));
    }

    @DeleteMapping("/{id}/favoritos/{produtoId}")
    public ResponseEntity<ClienteDetailDTO> removerFavorito(
            @PathVariable Long id, @PathVariable Long produtoId) {
        return ResponseEntity.ok(this.clienteService.removerFavorito(id, produtoId));
    }
}
