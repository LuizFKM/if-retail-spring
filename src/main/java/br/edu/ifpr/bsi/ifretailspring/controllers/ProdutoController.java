package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:5173") //TODO remover ao fazer deploy
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // READ - Listar todos os produtos (GET)
    @GetMapping
    public ResponseEntity<List<ProdutoDetailDTO>> listarProdutos() {
        List<ProdutoDetailDTO> produtos = this.produtoService.listar();
        return ResponseEntity.ok(produtos);
    }

    // READ - Buscar produto por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetailDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDetailDTO produto = this.produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // READ - Listar produtos sem estoque (GET)
    @GetMapping("/sem-estoque")
    public ResponseEntity<List<ProdutoDetailDTO>> listarSemEstoque() {
        List<ProdutoDetailDTO> produtos = this.produtoService.listarSemEstoque();
        return ResponseEntity.ok(produtos);
    }

    // CREATE - Criar um novo produto (POST)
    @PostMapping
    public ResponseEntity<ProdutoDetailDTO> criar(@RequestBody ProdutoRequestDTO request) {
        ProdutoDetailDTO produtoSalvo = this.produtoService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
    }

    // UPDATE - Atualizar um produto existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDetailDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody ProdutoRequestDTO request) {
        ProdutoDetailDTO produtoAtualizado = this.produtoService.atualizar(id, request);
        return ResponseEntity.ok(produtoAtualizado);
    }

    // DELETE - Excluir um produto pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.produtoService.excluir(id);
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<ProdutoDetailDTO> fazerUploadImagem(
            @PathVariable Long id,
            @RequestParam("img") MultipartFile imagem) {

        ProdutoDetailDTO produtoAtualizado = this.produtoService.salvarImagem(id, imagem);

        return ResponseEntity.ok(produtoAtualizado);
    }


}
