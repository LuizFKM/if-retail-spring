package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // READ - Listar todos os endereços (GET)
    @GetMapping
    public ResponseEntity<List<Endereco>> listarEnderecos() {
        List<Endereco> enderecos = this.enderecoService.listar();
        return ResponseEntity.ok(enderecos);
    }

    // READ - Buscar endereço por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        Endereco endereco = this.enderecoService.buscarPorId(id);
        return ResponseEntity.ok(endereco);
    }

    // CREATE - Criar um novo endereço (POST)
    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco request) {
        Endereco enderecoSalvo = this.enderecoService.salvar(request);
        return ResponseEntity.ok(enderecoSalvo);
    }

    // UPDATE - Atualizar um endereço existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id,
                                               @RequestBody Endereco request) {
        Endereco enderecoAtualizado = this.enderecoService.atualizar(id, request);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    // DELETE - Excluir um endereço pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.enderecoService.excluir(id);
    }
}
