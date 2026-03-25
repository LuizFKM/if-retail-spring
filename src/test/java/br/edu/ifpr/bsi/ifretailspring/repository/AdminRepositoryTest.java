package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import br.edu.ifpr.bsi.ifretailspring.domain.factory.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class AdminRepositoryTest {
    @Autowired
    AdminRepository adminRepository;

    @Test
    public void testInserir(){
        Admin admin = new Admin();
        admin.setName("Tom Jobim");
        admin.setCpf("123.233.444-90");
        admin.setPassword("123123123123");
        admin.setSetor("Administrativo");
        admin.setCargo("Administrador");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);

        adminRepository.save(admin);
        Admin adminInserido = adminRepository.findById(admin.getID()).get();

        Assertions.assertNotNull(adminInserido, "Admin não foi inserido");
    }

    @Test
    public void testInserirComFactory(){
        Admin admin = (Admin) UserFactory.createUser(UserType.ADMIN);
        admin.setName("Milton Nascimento");
        admin.setCpf("654.654.222-90");
        admin.setPassword("123123123123");
        admin.setSetor("Financeiro");
        admin.setCargo("Rei");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);

        adminRepository.save(admin);
        Admin adminInserido = adminRepository.findById(admin.getID()).get();

        Assertions.assertNotNull(adminInserido, "Admin não foi inserido");

    }


    @Test
    public void testUpdate(){
        Admin admin = new Admin();
        admin.setName("Tom Jobim");
        admin.setCpf("123.233.444-90");
        admin.setPassword("123123123123");
        admin.setCargo("Administrador");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);
        admin = adminRepository.save(admin);

        admin.setCargo("Chefe");
        admin = adminRepository.save(admin);

        Admin adminAtualizado = adminRepository.findById(admin.getID()).get();
        Assertions.assertEquals("Chefe", adminAtualizado.getCargo(),
                "O cargo não foi atualizado");

    }

    @Test
    public void testDelete(){
        Admin admin = new Admin();
        admin.setName("Tom Jobim");
        admin.setCpf("123.233.444-90");
        admin.setPassword("123123123123");
        admin.setCargo("Administrador");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);
        admin = adminRepository.save(admin);

        adminRepository.delete(admin);

        Admin adminDeletado = adminRepository.findById(admin.getID()).orElse(null);
        Assertions.assertNull(adminDeletado, "admin ainda se encontra no banco");
    }

    @Test
    public void testFindByCpf(){
        Admin admin = new Admin();
        admin.setName("Tom Jobim");
        admin.setCpf("123.233.444-90");
        admin.setPassword("123123123123");
        admin.setSetor("Administrativo");
        admin.setCargo("Administrador");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);
        adminRepository.save(admin);

        Admin encontrado = adminRepository.findByCpf(admin.getCpf())
                .stream().findFirst().orElse(null);

        Assertions.assertNotNull(encontrado, "Admin encontrado");
    }

    @Test
    public void testFindByName(){
        Admin a1 = new Admin();
        a1.setName("Milton Nascimento");
        a1.setCpf("111.222.333-44");
        a1.setPassword("bituca123");
        a1.setTipo(UserType.ADMIN);

        Admin a2 = new Admin();
        a2.setName("Lô Borges");
        a2.setCpf("555.666.777-88");
        a2.setPassword("clube123");
        a2.setTipo(UserType.ADMIN);

        adminRepository.saveAll(List.of(a1, a2));
        List<Admin> encontrados = adminRepository.findByName("Milton Nascimento");
        Assertions.assertNotNull(encontrados, "nome encontrado");
    }


    @Test
    public void testGetAllByNameLike(){
        Admin a1 = new Admin();
        a1.setName("Gilberto Gil");
        a1.setCpf("111.111.111-11");
        a1.setPassword("senha123");
        a1.setTipo(UserType.ADMIN);

        Admin a2 = new Admin();
        a2.setName("Gilberto Passos");
        a2.setCpf("222.222.222-22");
        a2.setPassword("senha456");
        a2.setTipo(UserType.ADMIN);

        adminRepository.save(a1);
        adminRepository.save(a2);

        List<Admin> resultado = adminRepository.getAllByNameLike("Gil");
        Assertions.assertNotNull(resultado, "nome encontrado");

    }

    @Test
    public void testListar(){
        Admin admin = new Admin();
        admin.setName("Tom Jobim");
        admin.setCpf("123.233.444-90");
        admin.setPassword("123123123123");
        admin.setSetor("Administrativo");
        admin.setCargo("Administrador");
        admin.setMatricula("509-e");
        admin.setDataAdmissao(LocalDate.of(2005, 9, 10));
        admin.setTipo(UserType.ADMIN);
        adminRepository.save(admin);

        long inicio = System.currentTimeMillis();
        List<Admin> admins = adminRepository.findAll();
        long fim = System.currentTimeMillis();

        Assertions.assertFalse(admins.isEmpty(), "A listagem não retornou resultados.");
        Assertions.assertTrue((fim - inicio) < 2000, "A consulta demorou mais de 2 segundos.");

    }


}
