package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.mappers.PedidoMapper;
import br.edu.ifpr.bsi.ifretailspring.mappers.ProdutoMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.PedidoRepository;
import br.edu.ifpr.bsi.ifretailspring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;

    public List<PedidoDetailDTO> listar() {
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        return pedidos.stream().map(this.pedidoMapper::entityToDetailDTO).toList();
    }

    public PedidoDetailDTO buscarPorId(Long id) {
        Pedido pedidoEncontrado = this.pedidoRepository.findById(id).orElse(null);
        if(pedidoEncontrado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pedido nao encontrado");
        }
        return this.pedidoMapper.entityToDetailDTO(pedidoEncontrado);
    }

    @Transactional
    public PedidoDetailDTO salvar(PedidoRequestDTO request) {
        Pedido pedido = this.pedidoMapper.requestDTOToEntity(request);
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível criar um pedido sem itens.");
        }

        pedido.setDataDeEntregaDoPedido(LocalDateTime.now().plusDays(7));
        pedido.setStatus(true);

        pedido.getItems().forEach(itemPedido -> {
            Produto produto = produtoRepository.findById(itemPedido.getProduto().getID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
            itemPedido.setProduto(produto);
            itemPedido.setPedido(pedido);
        });

        return this.pedidoMapper.entityToDetailDTO(pedido);
    }

    @Transactional
    public Pedido atualizar(Long id, Pedido pedido) {
        this.pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pedido não encontrado"));
        pedido.setID(id);
        return this.pedidoRepository.save(pedido);
    }

    // Cancelar seta status=false em vez de deletar fisicamente
    @Transactional
    public void cancelar(Long id) {
        Pedido pedido = this.pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pedido não encontrado"));
        pedido.setStatus(false);
        this.pedidoRepository.save(pedido);
    }

    @Transactional
    public void excluir(Long id) {
        this.pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pedido não encontrado"));
        this.pedidoRepository.deleteById(id);
    }
}
