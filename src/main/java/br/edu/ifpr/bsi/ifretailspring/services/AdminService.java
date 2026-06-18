package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.mappers.AdminMapper;
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

    @Autowired
    private AdminMapper adminMapper;

    public List<AdminDetailDTO> listar() {
        List<Admin> admins = this.adminRepository.findAll();
        return admins.stream().map(this.adminMapper::entityToDetailDTO).toList();
    }

    public AdminDetailDTO buscarPorId(Long id) {
        Admin adminEncontrado = this.adminRepository.findById(id).orElse(null);
        if(adminEncontrado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado");
        }
        return this.adminMapper.entityToDetailDTO(adminEncontrado);
    }

    public List<AdminDetailDTO> buscarPorCpf(String cpf) {
        List<Admin> adminEncontrado = this.adminRepository.findByCpf(cpf);
        if(adminEncontrado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionario nao encontrado");
        }
        return adminEncontrado.stream().map(this.adminMapper::entityToDetailDTO).toList();
    }

    public List<AdminDetailDTO> buscarPorNome(String name) {
        List<Admin> adminEncontrado = this.adminRepository.getAllByNameLike(name);
        return adminEncontrado.stream().map(this.adminMapper::entityToDetailDTO).toList();
    }

    @Transactional
    public AdminDetailDTO salvar(AdminRequestDTO request) {
        Admin admin = this.adminMapper.requestDTOToEntity(request);
        if(admin.getContatoList() != null && !admin.getContatoList().isEmpty()){
            admin.getContatoList().forEach(contato-> contato.setUser(admin));
        }

        return this.adminMapper.entityToDetailDTO(this.adminRepository.save(admin));

    }

    @Transactional
    public AdminDetailDTO atualizar(Long id, AdminRequestDTO request) {
        this.adminRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Admin não encontrado"));
        Admin admin = this.adminMapper.requestDTOToEntity(request);
        admin.setId(id);
        return this.adminMapper.entityToDetailDTO(this.adminRepository.save(admin));
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
