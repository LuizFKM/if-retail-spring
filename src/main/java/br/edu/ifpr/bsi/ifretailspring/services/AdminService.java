package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.mappers.AdminMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    public Page<AdminDetailDTO> listar(Pageable pageable) {
        return this.adminRepository.findAll(pageable).map(this.adminMapper::entityToDetailDTO);
    }

    public AdminDetailDTO buscarPorId(Long id) {
        Admin admin = this.adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado"));
        return this.adminMapper.entityToDetailDTO(admin);
    }

    public List<AdminDetailDTO> buscarPorCpf(String cpf) {
        List<Admin> admins = this.adminRepository.findByCpf(cpf);
        if (admins == null || admins.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado");
        }
        return admins.stream().map(this.adminMapper::entityToDetailDTO).toList();
    }

    public List<AdminDetailDTO> buscarPorNome(String name) {
        return this.adminRepository.getAllByNameLike(name)
                .stream().map(this.adminMapper::entityToDetailDTO).toList();
    }

    @Transactional
    public AdminDetailDTO salvar(AdminRequestDTO request) {
        Admin admin = this.adminMapper.requestDTOToEntity(request);
        admin.setRole(UserRole.ADMIN); // sempre ADMIN, independente do que vier no request
        if (admin.getContatoList() != null && !admin.getContatoList().isEmpty()) {
            admin.getContatoList().forEach(contato -> contato.setUser(admin));
        }
        return this.adminMapper.entityToDetailDTO(this.adminRepository.save(admin));
    }

    @Transactional
    public AdminDetailDTO atualizar(Long id, AdminRequestDTO request) {
        Admin admin = this.adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado"));
        // updateFromDto preserva urlFotoPerfil e outros campos não presentes no request
        this.adminMapper.updateFromDto(request, admin);
        if (admin.getContatoList() != null) {
            admin.getContatoList().forEach(contato -> contato.setUser(admin));
        }
        return this.adminMapper.entityToDetailDTO(this.adminRepository.save(admin));
    }

    @Transactional
    public void excluir(Long id) {
        this.adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin não encontrado"));
        this.adminRepository.deleteById(id);
    }
}
