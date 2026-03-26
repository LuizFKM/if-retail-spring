package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public List<Contato> listar() {
        return this.contatoRepository.findAll();
    }

    public Contato buscarPorId(Long id) {
        return this.contatoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Contato não encontrado"));
    }

    public Contato salvar(Contato contato) {
        return this.contatoRepository.save(contato);
    }

    public Contato atualizar(Long id, Contato contato) {
        this.contatoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Contato não encontrado"));
        contato.setID(id);
        return this.contatoRepository.save(contato);
    }

    @Transactional
    public void excluir(Long id) {
        this.contatoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Contato não encontrado"));
        this.contatoRepository.deleteById(id);
    }
}
