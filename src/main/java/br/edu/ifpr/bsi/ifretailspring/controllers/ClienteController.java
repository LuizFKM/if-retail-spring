package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // READ - Listar todos os clientes (GET)
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = this.clienteService.listar();
        return ResponseEntity.ok(clientes);
    }

    // READ - Buscar cliente por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = this.clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    // READ - Buscar clientes por CPF (GET)
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<Cliente>> buscarPorCpf(@PathVariable String cpf) {
        List<Cliente> clientes = this.clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(clientes);
    }

    // READ - Buscar clientes por nome (GET)
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<Cliente>> buscarPorNome(@PathVariable String name) {
        List<Cliente> clientes = this.clienteService.buscarPorNome(name);
        return ResponseEntity.ok(clientes);
    }

    // CREATE - Criar um novo cliente (POST)
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente request) {
        Cliente clienteSalvo = this.clienteService.salvar(request);
        return ResponseEntity.ok(clienteSalvo);
    }

    // UPDATE - Atualizar um cliente existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id,
                                              @RequestBody Cliente request) {
        Cliente clienteAtualizado = this.clienteService.atualizar(id, request);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // DELETE - Excluir um cliente pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.clienteService.excluir(id);
    }
}
