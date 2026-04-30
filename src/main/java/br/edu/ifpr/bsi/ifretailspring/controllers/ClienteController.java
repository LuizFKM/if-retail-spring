package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
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
    public ResponseEntity<List<ClienteDetailDTO>> listarClientes() {
        List<ClienteDetailDTO> clientes = this.clienteService.listar();
        return ResponseEntity.ok(clientes);
    }

    // READ - Buscar cliente por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetailDTO> buscarPorId(@PathVariable Long id) {
        ClienteDetailDTO cliente = this.clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    // READ - Buscar clientes por CPF (GET)
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorCpf(@PathVariable String cpf) {
        List<ClienteDetailDTO> clientes = this.clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(clientes);
    }

    // READ - Buscar clientes por nome (GET)
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorNome(@PathVariable String name) {
        List<ClienteDetailDTO> clientes = this.clienteService.buscarPorNome(name);
        return ResponseEntity.ok(clientes);
    }

    // CREATE - Criar um novo cliente (POST)
    @PostMapping
    public ResponseEntity<ClienteDetailDTO> criar(@RequestBody ClienteRequestDTO request) {
        ClienteDetailDTO clienteSalvo = this.clienteService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    // UPDATE - Atualizar um cliente existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDetailDTO> atualizar(@PathVariable Long id,
                                              @RequestBody ClienteRequestDTO request) {
        ClienteDetailDTO clienteAtualizado = this.clienteService.atualizar(id, request);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // DELETE - Excluir um cliente pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.clienteService.excluir(id);
    }
}
