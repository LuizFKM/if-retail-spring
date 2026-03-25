package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByCpf(String CPF);
    List<Admin> findByName(String name);

    @Query(value="SELECT a FROM Admin a WHERE a.name LIKE %:name%")
    List<Admin> getAllByNameLike(@Param("name") String nome);
}
