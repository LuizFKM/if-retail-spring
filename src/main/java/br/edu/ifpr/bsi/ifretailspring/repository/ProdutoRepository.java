package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEmEstoque = 0")
    List<Produto> findProdutosSemEstoque();
}
