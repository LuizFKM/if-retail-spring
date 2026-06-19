package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.mappers.ClienteMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.ClienteRepository;
import br.edu.ifpr.bsi.ifretailspring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private StorageService storageService;

    public Page<ClienteDetailDTO> listar(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(this.clienteMapper::entityToDetailDTO);
    }

    @Transactional
    public ClienteDetailDTO atualizarFoto(Long id, MultipartFile imagem) {
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        String urlImagem = storageService.upload("clientes-api-spring", imagem, "imagem-cliente-" + id);
        cliente.setUrlFotoPerfil(urlImagem);
        return this.clienteMapper.entityToDetailDTO(this.clienteRepository.save(cliente));
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
        Cliente cliente = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        cliente.getPedidoList().clear(); // cascade ALL remove os pedidos antes do delete
        cliente.getFavoritos().clear();
        this.clienteRepository.save(cliente);
        this.clienteRepository.deleteById(id);
    }

    @Transactional
    public ClienteDetailDTO adicionarFavorito(Long clienteId, Long produtoId) {
        Cliente cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        Produto produto = this.produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        if (!cliente.getFavoritos().contains(produto)) {
            cliente.getFavoritos().add(produto);
            this.clienteRepository.save(cliente);
        }
        return this.clienteMapper.entityToDetailDTO(cliente);
    }

    @Transactional
    public ClienteDetailDTO removerFavorito(Long clienteId, Long produtoId) {
        Cliente cliente = this.clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        cliente.getFavoritos().removeIf(p -> p.getId().equals(produtoId));
        this.clienteRepository.save(cliente);
        return this.clienteMapper.entityToDetailDTO(cliente);
    }
}
