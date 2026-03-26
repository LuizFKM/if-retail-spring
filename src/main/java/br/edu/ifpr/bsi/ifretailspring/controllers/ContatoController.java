package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    // READ - Listar todos os contatos (GET)
    @GetMapping
    public ResponseEntity<List<Contato>> listarContatos() {
        List<Contato> contatos = this.contatoService.listar();
        return ResponseEntity.ok(contatos);
    }

    // READ - Buscar contato por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscarPorId(@PathVariable Long id) {
        Contato contato = this.contatoService.buscarPorId(id);
        return ResponseEntity.ok(contato);
    }

    // CREATE - Criar um novo contato (POST)
    @PostMapping
    public ResponseEntity<Contato> criar(@RequestBody Contato request) {
        Contato contatoSalvo = this.contatoService.salvar(request);
        return ResponseEntity.ok(contatoSalvo);
    }

    // UPDATE - Atualizar um contato existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Contato> atualizar(@PathVariable Long id,
                                              @RequestBody Contato request) {
        Contato contatoAtualizado = this.contatoService.atualizar(id, request);
        return ResponseEntity.ok(contatoAtualizado);
    }

    // DELETE - Excluir um contato pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.contatoService.excluir(id);
    }
}
