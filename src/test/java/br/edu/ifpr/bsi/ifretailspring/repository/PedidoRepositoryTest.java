package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setDescricao("Notebook Dell Inspiron");
        produto.setPrecoUnitario(3500.00);
        produto.setQuantidadeEmEstoque(10);
        produto = produtoRepository.save(produto);




        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(2);

        Pedido pedidoNovo = new Pedido();

        item.setPedido(pedidoNovo);
        pedidoNovo.setCliente(cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedidoNovo);

        Assertions.assertNotNull(pedidoSalvo, "O pedido não foi salvo");

    }

    @Test
    public void testListarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Assertions.assertNotNull(pedidos, "A lista de pedidos não deveria ser nula");
    }

    @Test
    public void testBuscarPedidoPorId() {
        Cliente cliente = new Cliente();
        cliente.setName("Milton Nascimento");
        cliente.setCpf("111.222.333-44");
        cliente.setPassword("senha123");
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        Produto produto = new Produto();
        produto.setDescricao("Teclado Mecânico");
        produto.setPrecoUnitario(450.00);
        produto.setQuantidadeEmEstoque(5);
        produto = produtoRepository.save(produto);

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(1);

        Pedido pedido = new Pedido();
        item.setPedido(pedido);
        pedido.setCliente(cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        Optional<Pedido> encontrado = pedidoRepository.findById(pedidoSalvo.getId());
        Assertions.assertTrue(encontrado.isPresent(), "O pedido deveria ser encontrado pelo ID");
        Assertions.assertEquals(pedidoSalvo.getId(), encontrado.get().getId());
    }

    @Test
    @Transactional
    public void testDeletarPedido() {
        Cliente cliente = new Cliente();
        cliente.setName("Chico Buarque");
        cliente.setCpf("999.888.777-66");
        cliente.setPassword("abc456");
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        Long id = pedidoSalvo.getId();

        pedidoRepository.deleteById(id);

        Optional<Pedido> deletado = pedidoRepository.findById(id);
        Assertions.assertFalse(deletado.isPresent(), "O pedido deveria ter sido deletado");
    }
}
