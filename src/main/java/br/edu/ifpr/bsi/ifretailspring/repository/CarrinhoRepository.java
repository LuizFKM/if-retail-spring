package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    @Query("SELECT c FROM Carrinho c LEFT JOIN FETCH c.itens WHERE c.ID = :id")
    Optional<Carrinho> findByIdWithItens(@Param("id") Long id);
}
