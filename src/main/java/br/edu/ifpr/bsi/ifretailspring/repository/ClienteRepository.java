package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Long> {
    List<Cliente> findByCpf(String CPF);
    List<Cliente> findByName(String name);

    @Query(value="SELECT c FROM Cliente c WHERE c.name LIKE %:name%")
    List<Cliente> getAllByNameLike(@Param("name") String nome);

}
