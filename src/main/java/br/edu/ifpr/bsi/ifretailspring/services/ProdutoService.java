package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.mappers.ProdutoMapper;
import br.edu.ifpr.bsi.ifretailspring.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private StorageService storageService;

    public Page<ProdutoDetailDTO> listar(Pageable pageable) {
        return this.produtoRepository.findAll(pageable).map(this.produtoMapper::entityToDetailDTO);
    }

    public ProdutoDetailDTO buscarPorId(Long id) {
        Produto produtoEncontrado = this.produtoRepository.findById(id).orElse(null);
        if(produtoEncontrado == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "produto nao encontrado");
        }
        return this.produtoMapper.entityToDetailDTO(produtoEncontrado);
    }

    public List<ProdutoDetailDTO> listarSemEstoque() {
        List<Produto> produtosSemEstoque = this.produtoRepository.findProdutosSemEstoque();
        return produtosSemEstoque.stream().map(this.produtoMapper::entityToDetailDTO).toList();
    }

    @Transactional
    public ProdutoDetailDTO salvar(ProdutoRequestDTO request) {
        Produto produto = this.produtoMapper.requestDTOToEntity(request);
        return this.produtoMapper.entityToDetailDTO(this.produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoDetailDTO atualizar(Long id, ProdutoRequestDTO request) {
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
        this.produtoMapper.updateFromDto(request, produto);
        return this.produtoMapper.entityToDetailDTO(this.produtoRepository.save(produto));
    }

    @Transactional
    public void excluir(Long id) {
        this.produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
        this.produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoDetailDTO salvarImagem(Long id, MultipartFile imagem){
        Produto produto = this.produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        try{
            if(imagem != null){
                String urlImagem = storageService.upload("produto-api-imagem", imagem,
                        "imagem-cliente" + id);
                produto.setUrlFotoProduto(urlImagem);
            }

            Produto produtoComImagem = this.produtoRepository.save(produto);
            return this.produtoMapper.entityToDetailDTO(produtoComImagem);

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar imagem");
        }
    }

}
