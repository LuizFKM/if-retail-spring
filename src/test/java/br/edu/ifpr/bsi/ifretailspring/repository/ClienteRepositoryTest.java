package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.domain.factory.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    @Test
    public void testInserir(){
        Cliente cliente = new Cliente();
        cliente.setName("Elis Regina");
        cliente.setCpf("123.234.345-60");
        cliente.setPassword("1a2b3c");
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        Cliente clienteInserido = clienteRepository.findById(cliente.getId()).get();


        Assertions.assertNotNull(clienteInserido, "Cliente não foi inserido");

    }



    @Test
    public void testUpdate() {
        Cliente cliente = new Cliente();
        cliente.setName("Maria Antunes");
        cliente.setCpf("987.654.321-00");
        cliente.setPassword("hash");
        cliente.setRole(UserRole.CLIENTE);
        cliente = clienteRepository.save(cliente);

        cliente.setName("Maria Antunes Silva");
        cliente = clienteRepository.save(cliente);

        Cliente clienteAtualizado = clienteRepository.findById(cliente.getId()).get();

        Assertions.assertEquals("Maria Antunes Silva", clienteAtualizado.getName(),
                "O nome do cliente não foi atualizado.");
    }

    @Test
    public void testDelete(){
        Cliente cliente = new Cliente();
        cliente.setName("Tom Jobim");
        cliente.setCpf("123.234.345-60");
        cliente.setPassword("1a2b3c");
        cliente.setRole(UserRole.CLIENTE);
        Cliente clienteDeletar = clienteRepository.save(cliente);

        clienteRepository.delete(clienteDeletar);

        Cliente clienteDeletado = clienteRepository.findById(cliente.getId()).orElse(null);
        Assertions.assertNull(clienteDeletado, "O cliente aidna está no banco");

    }

    @Test
    public void testFindByCpf(){
        Cliente cliente = new Cliente();
        cliente.setName("Ana Pereira");
        cliente.setCpf("333.333.333-33");
        cliente.setPassword("1a2b3c");
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        Cliente encontrado = clienteRepository.findByCpf("333.333.333-33")
                .stream().findFirst().orElse(null);

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("Ana Pereira", encontrado.getName());
    }
    @Test
    public void testGetAllByNameLike(){
        Cliente c1 = new Cliente();
        c1.setName("Gilberto Gil");
        c1.setCpf("111.111.111-11");
        c1.setPassword("senha123");
        c1.setRole(UserRole.CLIENTE);

        Cliente c2 = new Cliente();
        c2.setName("Gilberto Passos");
        c2.setCpf("222.222.222-22");
        c2.setPassword("senha456");
        c2.setRole(UserRole.CLIENTE);

        clienteRepository.save(c1);
        clienteRepository.save(c2);

        List<Cliente> resultado = clienteRepository.getAllByNameLike("Gil");
        Assertions.assertNotNull(resultado, "nome encontrado");

    }

    @Test
    public void testFindByName(){
        Cliente c1 = new Cliente();
        c1.setName("Milton Nascimento");
        c1.setCpf("111.222.333-44");
        c1.setPassword("bituca123");
        c1.setRole(UserRole.CLIENTE);

        Cliente c2 = new Cliente();
        c2.setName("Lô Borges");
        c2.setCpf("555.666.777-88");
        c2.setPassword("clube123");
        c2.setRole(UserRole.CLIENTE);

        clienteRepository.saveAll(List.of(c1, c2));
        List<Cliente> encontrados = clienteRepository.findByName("Milton Nascimento");
        Assertions.assertNotNull(encontrados, "nome encontrado");
    }


    @Test
    public void testListar(){
        Cliente cliente = new Cliente();
        cliente.setName("Tom Jobim");
        cliente.setCpf("123.234.345-60");
        cliente.setPassword("1a2b3c");
        cliente.setRole(UserRole.CLIENTE);
        clienteRepository.save(cliente);

        long inicio = System.currentTimeMillis();
        List<Cliente> clientes = clienteRepository.findAll();
        long fim = System.currentTimeMillis();

        Assertions.assertFalse(clientes.isEmpty(), "A listagem não retornou resultados.");
        Assertions.assertTrue((fim - inicio) < 2000, "A consulta demorou mais de 2 segundos.");

    }
}
