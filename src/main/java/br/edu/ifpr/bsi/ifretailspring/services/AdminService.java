package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> listar() {
        return this.adminRepository.findAll();
    }

    public Admin buscarPorId(Long id) {
        return this.adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Admin não encontrado"));
    }

    public List<Admin> buscarPorCpf(String cpf) {
        return this.adminRepository.findByCpf(cpf);
    }

    public List<Admin> buscarPorNome(String name) {
        return this.adminRepository.getAllByNameLike(name);
    }

    @Transactional
    public Admin salvar(Admin admin) {
        return this.adminRepository.save(admin);
    }

    @Transactional
    public Admin atualizar(Long id, Admin admin) {
        this.adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Admin não encontrado"));
        admin.setID(id);
        return this.adminRepository.save(admin);
    }

    @Transactional
    public void excluir(Long id) {
        this.adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Admin não encontrado"));
        this.adminRepository.deleteById(id);
    }
}
