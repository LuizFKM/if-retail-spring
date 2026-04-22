package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
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

    public List<Pedido> listar() {
        return this.pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return this.pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Pedido não encontrado"));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        // Valida itens usando ResponseStatusException em vez de RuntimeException genérica
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível criar um pedido sem itens.");
        }

        // dataDoPedido é preenchida pelo @CreationTimestamp; apenas dataDeEntrega é definida manualmente
        pedido.setDataDeEntregaDoPedido(LocalDateTime.now().plusDays(7));
        pedido.setStatus(true);

        pedido.getItems().forEach(itemPedido -> {
            // Busca o Produto gerenciado pelo Hibernate para evitar TransientPropertyValueException
            Produto produto = produtoRepository.findById(itemPedido.getProduto().getID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
            itemPedido.setProduto(produto);
            itemPedido.setPedido(pedido);
        });

        return pedidoRepository.save(pedido);
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
