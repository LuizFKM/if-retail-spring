package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // READ - Listar todos os admins (GET)
    @GetMapping
    public ResponseEntity<List<AdminDetailDTO>> listarAdmins() {
        List<AdminDetailDTO> admins = this.adminService.listar();
        return ResponseEntity.ok(admins);
    }

    // READ - Buscar admin por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<AdminDetailDTO> buscarPorId(@PathVariable Long id) {
        AdminDetailDTO admin = this.adminService.buscarPorId(id);
        return ResponseEntity.ok(admin);
    }

    // READ - Buscar admins por CPF (GET)
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<AdminDetailDTO>> buscarPorCpf(@PathVariable String cpf) {
        List<AdminDetailDTO> admins = this.adminService.buscarPorCpf(cpf);
        return ResponseEntity.ok(admins);
    }

    // READ - Buscar admins por nome (GET)
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<AdminDetailDTO>> buscarPorNome(@PathVariable String name) {
        List<AdminDetailDTO> admins = this.adminService.buscarPorNome(name);
        return ResponseEntity.ok(admins);
    }

    // CREATE - Criar um novo admin (POST)
    @PostMapping
    public ResponseEntity<AdminDetailDTO> criar(@RequestBody AdminRequestDTO request) {
        AdminDetailDTO adminSalvo = this.adminService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminSalvo);
    }

    // UPDATE - Atualizar um admin existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<AdminDetailDTO> atualizar(@PathVariable Long id,
                                           @RequestBody AdminRequestDTO request) {
        AdminDetailDTO adminAtualizado = this.adminService.atualizar(id, request);
        return ResponseEntity.ok(adminAtualizado);
    }

    // DELETE - Excluir um admin pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.adminService.excluir(id);
    }
}
