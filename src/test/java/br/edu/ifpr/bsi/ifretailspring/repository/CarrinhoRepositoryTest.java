package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.ItemCarrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CarrinhoRepositoryTest {
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;


    private Cliente criarCliente(String nome, String cpf) {
        Carrinho carrinho = new Carrinho();
        Cliente c = new Cliente();
        c.setName(nome);
        c.setCpf(cpf);
        c.setPassword("123");
        c.setTipo(UserType.CLIENTE);
        c.setCarrinho(carrinho);
        return clienteRepository.save(c);
    }

    private Produto criarProduto(String descricao, double preco) {
        Produto p = new Produto();
        p.setDescricao(descricao);
        p.setPrecoUnitario(preco);
        p.setQuantidadeEmEstoque(20);
        p.setStatus(true);
        return produtoRepository.save(p);
    }
    @Test
    public void testInsert() {
        Carrinho carrinho = new Carrinho();
        carrinho = carrinhoRepository.save(carrinho);

        Carrinho carrinhoInserido = carrinhoRepository.findById(carrinho.getID()).get();

        Assertions.assertNotNull(carrinhoInserido, "O carrinho não foi inserido.");
    }

    @Test
    public void testInserirComItens() {
        Produto produto1 = criarProduto("Teclado", 150.00);
        Produto produto2 = criarProduto("Mouse", 80.00);

        Carrinho carrinho = new Carrinho();
        carrinho = carrinhoRepository.save(carrinho);

        ItemCarrinho item1 = new ItemCarrinho();
        item1.setCarrinho(carrinho);
        item1.setProduto(produto1);
        item1.setQuantidade(2);
        item1.setPrecoUnitario(150.00);

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setCarrinho(carrinho);
        item2.setProduto(produto2);
        item2.setQuantidade(1);
        item2.setPrecoUnitario(80.00);

        carrinho.getItens().add(item1);
        carrinho.getItens().add(item2);
        carrinhoRepository.save(carrinho);

        Carrinho carrinhoComItens = carrinhoRepository.findByIdWithItens(carrinho.getID()).get();

        Assertions.assertEquals(2, carrinhoComItens.getItens().size(),
                "Os itens do carrinho não foram inseridos corretamente (cascade ALL).");
    }

    @Test
    public void testRemoverItemDoCarrinho() {
        Produto p1 = criarProduto("Monitor", 1200.00);
        Produto p2 = criarProduto("Cabo HDMI", 80.00);

        p2.setQuantidadeEmEstoque(10);
        p2.setStatus(true);

        produtoRepository.save(p1);
        produtoRepository.save(p2);

        Carrinho carrinho = new Carrinho();
        carrinhoRepository.save(carrinho);

        ItemCarrinho item1 = new ItemCarrinho();
        item1.setCarrinho(carrinho);
        item1.setProduto(p1);
        item1.setQuantidade(1);
        item1.setPrecoUnitario(1200.0);

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setCarrinho(carrinho);
        item2.setProduto(p2);
        item2.setQuantidade(1);
        item2.setPrecoUnitario(50.0);

        carrinho.getItens().add(item1);
        carrinho.getItens().add(item2);
        carrinhoRepository.save(carrinho);

        Carrinho salvo = carrinhoRepository.findByIdWithItens(carrinho.getID()).get();
        salvo.getItens().remove(0);
        carrinhoRepository.save(salvo);

        Carrinho atualizado = carrinhoRepository.findByIdWithItens(carrinho.getID()).get();
        Assertions.assertEquals(1, atualizado.getItens().size(), "O item deveria ter sido removido.");
        Assertions.assertEquals("Cabo HDMI", atualizado.getItens().get(0).getProduto().getDescricao());
    }

    @Test
    public void testListarItensCarrinho(){
        Produto p1 = criarProduto("Monitor", 1200.00);
        Produto p2 = criarProduto("Mouse", 80.00);

        produtoRepository.save(p1);
        produtoRepository.save(p2);

        Carrinho carrinho = new Carrinho();
        carrinhoRepository.save(carrinho);

        ItemCarrinho item1 = new ItemCarrinho();
        item1.setCarrinho(carrinho);
        item1.setProduto(p1);
        item1.setQuantidade(1);
        item1.setPrecoUnitario(1200.0);

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setCarrinho(carrinho);
        item2.setProduto(p2);
        item2.setQuantidade(1);
        item2.setPrecoUnitario(50.0);

        carrinho.getItens().add(item1);
        carrinho.getItens().add(item2);
        carrinhoRepository.save(carrinho);

        Carrinho buscado = carrinhoRepository.findByIdWithItens(carrinho.getID()).get();
        List<ItemCarrinho> itensDoCarrinho = buscado.getItens();

        Assertions.assertFalse(itensDoCarrinho.isEmpty(), "Não há nada no carrinho");

    }
}
