package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.mappers.ClienteMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private StorageService storageService;

    public List<ClienteDetailDTO> listar() {
        return clienteRepository.findAll()
                .stream().map(this.clienteMapper::entityToDetailDTO).toList();
    }

    public ClienteDetailDTO buscarPorId(Long id) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        return this.clienteMapper.entityToDetailDTO(cliente);
    }

    public List<ClienteDetailDTO> buscarPorCpf(String cpf) {
        List<Cliente> clientes = this.clienteRepository.findByCpf(cpf);
        if (clientes == null || clientes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
        return clientes.stream().map(this.clienteMapper::entityToDetailDTO).toList();
    }

    public List<ClienteDetailDTO> buscarPorNome(String name) {
        return clienteRepository.findByName(name)
                .stream().map(this.clienteMapper::entityToDetailDTO).toList();
    }

    @Transactional
    public ClienteDetailDTO salvar(ClienteRequestDTO request) {
        Cliente cliente = this.clienteMapper.requestDTOtoEntity(request);
        cliente.setRole(UserRole.CLIENTE); // sempre CLIENTE, independente do que vier no request
        if (cliente.getContatoList() != null && !cliente.getContatoList().isEmpty()) {
            cliente.getContatoList().forEach(contato -> contato.setUser(cliente));
        }
        return this.clienteMapper.entityToDetailDTO(this.clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteDetailDTO atualizar(Long id, ClienteRequestDTO request, MultipartFile imagem) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        this.clienteMapper.updateFromDto(request, cliente);

        // Garante referência bidirecional nos contatos após atualização
        if (cliente.getContatoList() != null) {
            cliente.getContatoList().forEach(contato -> contato.setUser(cliente));
        }

        if (imagem != null) {
            // Endpoint espera multipart com campo "imagem"
            String urlImagem = storageService.upload("clientes-api-spring", imagem, "imagem-cliente-" + id);
            cliente.setUrlFotoPerfil(urlImagem);
        }

        return this.clienteMapper.entityToDetailDTO(this.clienteRepository.save(cliente));
    }

    @Transactional
    public void excluir(Long id) {
        this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        this.clienteRepository.deleteById(id);
    }
}
