package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
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
    public ResponseEntity<List<Admin>> listarAdmins() {
        List<Admin> admins = this.adminService.listar();
        return ResponseEntity.ok(admins);
    }

    // READ - Buscar admin por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Admin> buscarPorId(@PathVariable Long id) {
        Admin admin = this.adminService.buscarPorId(id);
        return ResponseEntity.ok(admin);
    }

    // READ - Buscar admins por CPF (GET)
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<Admin>> buscarPorCpf(@PathVariable String cpf) {
        List<Admin> admins = this.adminService.buscarPorCpf(cpf);
        return ResponseEntity.ok(admins);
    }

    // READ - Buscar admins por nome (GET)
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<Admin>> buscarPorNome(@PathVariable String name) {
        List<Admin> admins = this.adminService.buscarPorNome(name);
        return ResponseEntity.ok(admins);
    }

    // CREATE - Criar um novo admin (POST)
    @PostMapping
    public ResponseEntity<Admin> criar(@RequestBody Admin request) {
        Admin adminSalvo = this.adminService.salvar(request);
        return ResponseEntity.ok(adminSalvo);
    }

    // UPDATE - Atualizar um admin existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Admin> atualizar(@PathVariable Long id,
                                           @RequestBody Admin request) {
        Admin adminAtualizado = this.adminService.atualizar(id, request);
        return ResponseEntity.ok(adminAtualizado);
    }

    // DELETE - Excluir um admin pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.adminService.excluir(id);
    }
}
