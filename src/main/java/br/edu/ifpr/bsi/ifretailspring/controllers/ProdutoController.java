package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // READ - Listar todos os produtos (GET)
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = this.produtoService.listar();
        return ResponseEntity.ok(produtos);
    }

    // READ - Buscar produto por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = this.produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // READ - Listar produtos sem estoque (GET)
    @GetMapping("/sem-estoque")
    public ResponseEntity<List<Produto>> listarSemEstoque() {
        List<Produto> produtos = this.produtoService.listarSemEstoque();
        return ResponseEntity.ok(produtos);
    }

    // CREATE - Criar um novo produto (POST)
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto request) {
        Produto produtoSalvo = this.produtoService.salvar(request);
        return ResponseEntity.ok(produtoSalvo);
    }

    // UPDATE - Atualizar um produto existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id,
                                              @RequestBody Produto request) {
        Produto produtoAtualizado = this.produtoService.atualizar(id, request);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // DELETE - Excluir um produto pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.produtoService.excluir(id);
    }
}
