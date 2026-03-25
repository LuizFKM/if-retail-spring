package br.edu.ifpr.bsi.ifretailspring.repository;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente criarCliente(String nome, String cpf) {
        Cliente c = new Cliente();
        c.setName(nome); c.setCpf(cpf);
        c.setPassword("hash"); c.setTipo(UserType.CLIENTE);
        return clienteRepository.save(c);
    }

    private Endereco novoEndereco(Cliente user, String rua, String cidade, String estado, String cep) {
        Endereco e = new Endereco();
        e.setUser(user);
        e.setRua(rua);
        e.setNumero("100");
        e.setBairro("Centro");
        e.setCidade(cidade);
        e.setEstado(estado);
        e.setCep(cep);
        e.setPais("Brasil");
        return e;
    }

    @Test
    public void testInsert() {
        Cliente cliente = criarCliente("Endereco Insert", "010.010.010-10");

        Endereco endereco = novoEndereco(cliente, "Rua Augusto Guimaraes", "Palmas", "PR", "85555-000");
        endereco = enderecoRepository.save(endereco);

        Endereco enderecoInserido = enderecoRepository.findById(endereco.getID()).get();

        Assertions.assertNotNull(enderecoInserido, "O endereço não foi inserido.");
        Assertions.assertEquals("Palmas", enderecoInserido.getCidade(), "A cidade não confere.");
    }

    @Test
    public void testUpdate() {
        Cliente cliente = criarCliente("Update Endereco", "030.030.030-30");

        Endereco endereco = novoEndereco(cliente, "Rua Antiga", "Curitiba", "PR", "80000-000");
        endereco = enderecoRepository.save(endereco);

        endereco.setRua("Rua Nova");
        endereco = enderecoRepository.save(endereco);

        Endereco enderecoAtualizado = enderecoRepository.findById(endereco.getID()).get();

        Assertions.assertEquals("Rua Nova", enderecoAtualizado.getRua(),
                "A rua do endereço não foi atualizada.");
    }

    @Test
    public void testDelete() {
        Cliente cliente = criarCliente("Delete Endereco", "040.040.040-40");

        Endereco endereco = novoEndereco(cliente, "Rua Deletar", "Maringá", "PR", "87000-000");
        endereco = enderecoRepository.save(endereco);

        enderecoRepository.delete(endereco);

        Endereco enderecoDeletado = enderecoRepository.findById(endereco.getID()).orElse(null);
        Assertions.assertNull(enderecoDeletado, "O endereço ainda se encontra no banco de dados.");
    }

    @Test
    public void testListar() {
        Cliente cliente = criarCliente("Listar Endereco", "050.050.050-50");

        enderecoRepository.save(novoEndereco(cliente, "Rua A", "Palmas", "PR", "85555-001"));
        enderecoRepository.save(novoEndereco(cliente, "Rua B", "Palmas", "PR", "85555-002"));

        long inicio = System.currentTimeMillis();
        List<Endereco> enderecos = enderecoRepository.findAll();
        long fim = System.currentTimeMillis();

        Assertions.assertFalse(enderecos.isEmpty(), "A listagem não retornou resultados.");
        Assertions.assertTrue((fim - inicio) < 300, "A consulta demorou mais de 0,3 segundos.");
    }

}
