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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<ProdutoDetailDTO> listar() {
        List<Produto> produtos = this.produtoRepository.findAll();
        return produtos.stream().map(this.produtoMapper::entityToDetailDTO).toList();
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
        this.produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
        Produto produto = this.produtoMapper.requestDTOToEntity(request);
        produto.setID(id);
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
}
