package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.mappers.PedidoMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.ClienteRepository;
import br.edu.ifpr.bsi.ifretailspring.repository.PedidoRepository;
import br.edu.ifpr.bsi.ifretailspring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDetailDTO> listar() {
        return this.pedidoRepository.findAll()
                .stream().map(this.pedidoMapper::entityToDetailDTO).toList();
    }

    public PedidoDetailDTO buscarPorId(Long id) {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        return this.pedidoMapper.entityToDetailDTO(pedido);
    }

    @Transactional
    public PedidoDetailDTO salvar(PedidoRequestDTO request) {
        if (request.itens() == null || request.itens().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível criar um pedido sem itens.");
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(true);

        request.itens().forEach(itemRequest -> {
            Produto produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Produto não encontrado: id " + itemRequest.produtoId()));
            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade());
            item.setPedido(pedido);
            pedido.getItems().add(item);
        });

        Pedido pedidoSalvo = this.pedidoRepository.save(pedido); // era ausente — pedido nunca era persistido
        return this.pedidoMapper.entityToDetailDTO(pedidoSalvo);
    }

    // Cancelar seta status=false em vez de deletar fisicamente
    @Transactional
    public void cancelar(Long id) {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        pedido.setStatus(false);
        this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluir(Long id) {
        this.pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        this.pedidoRepository.deleteById(id);
    }
}
