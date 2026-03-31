package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.services.PedidoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testCriarPedido(){
        Cliente cliente = new Cliente();
        cliente.setName("Elis Regina");
        cliente.setCpf("123.234.345-60");
        cliente.setPassword("1a2b3c");
        cliente.setTipo(UserType.CLIENTE);
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setDescricao("Notebook Dell Inspiron");
        produto.setPrecoUnitario(3500.00);
        produto.setQuantidadeEmEstoque(10);
        produto.setStatus(true);
        produto = produtoRepository.save(produto);




        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPrecoUnitario(150.0);

        Pedido pedidoNovo = new Pedido();

        item.setPedido(pedidoNovo);
        pedidoNovo.setCliente(cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedidoNovo);

        Assertions.assertNotNull(pedidoSalvo, "O pedido não foi salvo");

    }
}
