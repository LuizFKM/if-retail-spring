package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
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

    public List<Produto> listar() {
        return this.produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return this.produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
    }

    public List<Produto> listarSemEstoque() {
        return this.produtoRepository.findProdutosSemEstoque();
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produto) {
        this.produtoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado"));
        produto.setID(id);
        return this.produtoRepository.save(produto);
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
