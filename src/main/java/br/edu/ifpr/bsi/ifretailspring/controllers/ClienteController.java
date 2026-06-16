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
@CrossOrigin(origins = "http://localhost:5173") //TODO Remover ao fazer deploy
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDetailDTO>> listarClientes() {
        List<ClienteDetailDTO> clientes = this.clienteService.listar();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDetailDTO> buscarPorId(@PathVariable Long id) {
        ClienteDetailDTO cliente = this.clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorCpf(@PathVariable String cpf) {
        List<ClienteDetailDTO> clientes = this.clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/nome/{name}")
    public ResponseEntity<List<ClienteDetailDTO>> buscarPorNome(@PathVariable String name) {
        List<ClienteDetailDTO> clientes = this.clienteService.buscarPorNome(name);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDetailDTO> criar(@RequestBody ClienteRequestDTO request) {
        ClienteDetailDTO clienteSalvo = this.clienteService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDetailDTO> atualizar(@PathVariable Long id,
                                              @RequestBody ClienteRequestDTO request) {
        ClienteDetailDTO clienteAtualizado = this.clienteService.atualizar(id, request);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.clienteService.excluir(id);
    }
}
