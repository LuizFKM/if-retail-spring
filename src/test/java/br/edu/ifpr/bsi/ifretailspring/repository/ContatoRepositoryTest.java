package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ContatoRepositoryTest {
    @Autowired
    private ContatoRepository contatoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente criarCliente(String nome, String cpf) {
        Cliente c = new Cliente();
        c.setName(nome);
        c.setCpf(cpf);
        c.setPassword("123"); c.setTipo(UserType.CLIENTE);
        return clienteRepository.save(c);
    }

    @Test
    public void testInsert() {
        Cliente cliente = criarCliente("Contato Teste", "010.020.030-40");

        Contato contato = new Contato();
        contato.setUser(cliente);
        contato.setTelefone("(46) 3025-1234");
        contato.setEmail("contato@teste.com");
        contato.setWhatsapp("(46) 99999-1234");
        contato = contatoRepository.save(contato);

        Contato contatoInserido = contatoRepository.findById(contato.getID()).get();

        Assertions.assertNotNull(contatoInserido, "O contato não foi inserido.");

    }

    @Test
    public void testUpdate() {
        Cliente cliente = criarCliente("Update Contato", "090.080.070-60");

        Contato contato = new Contato();
        contato.setUser(cliente);
        contato.setTelefone("(46) 1111-2222");
        contato.setEmail("antes@email.com");
        contato = contatoRepository.save(contato);

        contato.setEmail("depois@email.com");
        contato = contatoRepository.save(contato);

        Contato contatoAtualizado = contatoRepository.findById(contato.getID()).get();

        Assertions.assertEquals("depois@email.com", contatoAtualizado.getEmail(),
                "O e-mail do contato não foi atualizado.");
    }

    @Test
    public void testDelete() {
        Cliente cliente = criarCliente("Delete Contato", "110.110.110-11");

        Contato contato = new Contato();
        contato.setUser(cliente);
        contato.setTelefone("(46) 9999-8888");
        contato.setEmail("deletar@email.com");
        contato = contatoRepository.save(contato);

        contatoRepository.delete(contato);

        Contato contatoDeletado = contatoRepository.findById(contato.getID()).orElse(null);
        Assertions.assertNull(contatoDeletado, "O contato ainda se encontra no banco de dados.");
    }

    @Test
    public void testListar(){
        Cliente cliente = criarCliente("Delete Contato", "110.110.110-11");

        Contato contato = new Contato();
        contato.setUser(cliente);
        contato.setTelefone("(46) 9999-8888");
        contato.setEmail("deletar@email.com");
        contato = contatoRepository.save(contato);

        long inicio = System.currentTimeMillis();
        List<Contato> contatos = contatoRepository.findAll();
        long fim = System.currentTimeMillis();

        Assertions.assertFalse(contatos.isEmpty(), "A listagem não retornou resultados.");
        Assertions.assertTrue((fim - inicio) < 300, "A consulta demorou mais de 0,3 segundos.");

    }

}
