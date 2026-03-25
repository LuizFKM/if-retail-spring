package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    public void testInsert() {
        Produto produto = new Produto();
        produto.setDescricao("Notebook Dell Inspiron");
        produto.setPrecoUnitario(3500.00);
        produto.setQuantidadeEmEstoque(10);
        produto.setStatus(true);
        produto = produtoRepository.save(produto);

        Produto produtoInserido = produtoRepository.findById(produto.getID()).get();

        Assertions.assertNotNull(produtoInserido, "O produto não foi inserido.");
    }

    @Test
    public void testUpdate() {
        Produto produto = new Produto();
        produto.setDescricao("Mouse sem fio");
        produto.setPrecoUnitario(80.00);
        produto.setQuantidadeEmEstoque(50);
        produto.setStatus(true);
        produto = produtoRepository.save(produto);

        produto.setPrecoUnitario(65.00);
        produto = produtoRepository.save(produto);

        Produto produtoAtualizado = produtoRepository.findById(produto.getID()).get();

        Assertions.assertEquals(65.00, produtoAtualizado.getPrecoUnitario(),
                "O preço do produto não foi atualizado.");
    }

    @Test
    public void testDelete() {
        Produto produto = new Produto();
        produto.setDescricao("Teclado Mecânico");
        produto.setPrecoUnitario(250.00);
        produto.setQuantidadeEmEstoque(15);
        produto.setStatus(true);
        produto = produtoRepository.save(produto);

        produtoRepository.delete(produto);

        Produto produtoDeletado = produtoRepository.findById(produto.getID()).orElse(null);
        Assertions.assertNull(produtoDeletado, "O produto ainda se encontra no banco de dados.");
    }

    @Test
    public void testFindAll() {
        Produto p1 = new Produto();
        p1.setDescricao("Monitor LG 24'");
        p1.setPrecoUnitario(900.00);
        p1.setQuantidadeEmEstoque(5);
        p1.setStatus(true);
        produtoRepository.save(p1);

        Produto p2 = new Produto();
        p2.setDescricao("Headset Gamer");
        p2.setPrecoUnitario(350.00);
        p2.setQuantidadeEmEstoque(20);
        p2.setStatus(true);
        produtoRepository.save(p2);

        List<Produto> produtos = produtoRepository.findAll();

        Assertions.assertTrue(produtos.size() >= 2, "A lista deveria conter pelo menos 2 produtos.");
    }

    @Test
    public void testFindProdutosSemEstoque() {

        Produto p1 = new Produto();
        p1.setDescricao("Teclado Gamer");
        p1.setPrecoUnitario(150.00);
        p1.setQuantidadeEmEstoque(0);
        p1.setStatus(true);
        produtoRepository.save(p1);

        Produto p2 = new Produto();
        p2.setDescricao("Mousepad XL");
        p2.setPrecoUnitario(50.00);
        p2.setQuantidadeEmEstoque(10);
        p2.setStatus(true);
        produtoRepository.save(p2);


        List<Produto> esgotados = produtoRepository.findProdutosSemEstoque();


        Assertions.assertFalse(esgotados.isEmpty(), "A lista de esgotados não deveria estar vazia.");



    }


}
