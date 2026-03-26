package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return this.clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return this.clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
    }

    public List<Cliente> buscarPorCpf(String cpf) {
        return this.clienteRepository.findByCpf(cpf);
    }

    public List<Cliente> buscarPorNome(String name) {
        return this.clienteRepository.getAllByNameLike(name);
    }

    public Cliente salvar(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente cliente) {
        this.clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
        cliente.setID(id);
        return this.clienteRepository.save(cliente);
    }

    @Transactional
    public void excluir(Long id) {
        this.clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
        this.clienteRepository.deleteById(id);
    }
}
