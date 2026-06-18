package br.edu.ifpr.bsi.ifretailspring.service;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.repository.ProdutoRepository;
import br.edu.ifpr.bsi.ifretailspring.services.ProdutoService;
import br.edu.ifpr.bsi.ifretailspring.services.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        // AQUI: Podes editar a URL ou propriedades simuladas que o teu StorageService utiliza
        "storage.server.url=${cloudinary.url}",
        "storage.bucket.name=produto-api-imagem"
})
public class ProdutoServiceTest {
    @Autowired
    private ProdutoService produtoService;

    @MockitoBean
    private StorageService storageService;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto produtoSalvo;

    @BeforeEach
    void setUp() {

        produtoRepository.deleteAll();


        Produto produto = new Produto();
        produto.setDescricao("Teclado Mecânico Gamer");
        produto.setQuantidadeEmEstoque(15);
        produto.setPrecoUnitario(250.00);
        // Deixamos a URL da foto inicialmente nula
        produto.setUrlFotoProduto(null);

        this.produtoSalvo = produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Deve salvar a URL da imagem no produto com sucesso")
    void deveSalvarImagemComSucesso() {
        // Cenário: Arquivo simulado
        MockMultipartFile imagemSimulada = new MockMultipartFile(
                "imagem",
                "foto_produto.jpg",
                "image/jpeg",
                "conteudo_da_imagem_em_bytes".getBytes()
        );

        // 2. Ensinamos o Mock: Quando chamarem o método upload com qualquer string ou arquivo,
        // devolve esta URL falsa em vez de tentar aceder à internet.
        Mockito.when(storageService.upload(anyString(), any(), anyString()))
                .thenReturn("http://fake-cloudinary.com/minha-imagem.jpg");

        // Ação: Executa o método do serviço
        ProdutoDetailDTO resultado = produtoService.salvarImagem(produtoSalvo.getId(), imagemSimulada);

        // Verificação
        assertThat(resultado).isNotNull();

        // Verifica se persistiu corretamente buscando do banco novamente
        Produto produtoAtualizado = produtoRepository.findById(produtoSalvo.getId()).orElse(null);
        assertThat(produtoAtualizado).isNotNull();
        // Verifica se a URL guardada é a mesma que o nosso Mock devolveu
        assertThat(produtoAtualizado.getUrlFotoProduto()).isEqualTo("http://fake-cloudinary.com/minha-imagem.jpg");
    }
}


