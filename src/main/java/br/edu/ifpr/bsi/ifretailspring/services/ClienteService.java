package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.mappers.ClienteMapper;
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

    @Autowired
    private ClienteMapper clienteMapper;

    public List<ClienteDetailDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this.clienteMapper::entityToDetailDTO).toList();

    }
    public ClienteDetailDTO buscarPorId(Long id) {
        Cliente clienteEncontrado = this.clienteRepository.findById(id).orElse(null);
        if(clienteEncontrado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado");
        }
        return this.clienteMapper.entityToDetailDTO(clienteEncontrado);
    }

    public List<ClienteDetailDTO> buscarPorCpf(String cpf) {
        List<Cliente> clienteEncontrado = this.clienteRepository.findByCpf(cpf);
        if(clienteEncontrado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado");
        }
        return clienteEncontrado.stream().map(this.clienteMapper::entityToDetailDTO).toList();

    }

    public List<ClienteDetailDTO> buscarPorNome(String name) {
        List<Cliente> clientes = clienteRepository.findByName(name);
        return clientes.stream()
                .map(this.clienteMapper::entityToDetailDTO)
                .toList();
    }

    @Transactional
    public ClienteDetailDTO salvar(ClienteRequestDTO request) {
        Cliente cliente = this.clienteMapper.requestDTOtoEntity(request);
        if (cliente.getContatoList() != null && !cliente.getContatoList().isEmpty()){
            cliente.getContatoList().forEach(contato-> contato.setUser(cliente));
        }
        return this.clienteMapper.entityToDetailDTO(this.clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteDetailDTO atualizar(Long id, ClienteRequestDTO request) {
        this.clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado"));
        Cliente cliente = this.clienteMapper.requestDTOtoEntity(request);
        cliente.setID(id);
        return this.clienteMapper.entityToDetailDTO(this.clienteRepository.save(cliente));
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
