package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> listar() {
        return this.enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return this.enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Endereço não encontrado"));
    }

    public Endereco salvar(Endereco endereco) {
        return this.enderecoRepository.save(endereco);
    }

    public Endereco atualizar(Long id, Endereco endereco) {
        this.enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Endereço não encontrado"));
        endereco.setID(id);
        return this.enderecoRepository.save(endereco);
    }

    @Transactional
    public void excluir(Long id) {
        this.enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Endereço não encontrado"));
        this.enderecoRepository.deleteById(id);
    }
}
