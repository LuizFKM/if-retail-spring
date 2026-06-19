package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// CORS global via CorsConfig — @CrossOrigin removido daqui
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDetailDTO>> listarProdutos() {
        return ResponseEntity.ok(this.produtoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDetailDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(this.produtoService.buscarPorId(id));
    }

    @GetMapping("/sem-estoque")
    public ResponseEntity<List<ProdutoDetailDTO>> listarSemEstoque() {
        return ResponseEntity.ok(this.produtoService.listarSemEstoque());
    }

    @PostMapping
    public ResponseEntity<ProdutoDetailDTO> criar(@RequestBody ProdutoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.produtoService.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDetailDTO> atualizar(@PathVariable Long id,
                                                      @RequestBody ProdutoRequestDTO request) {
        return ResponseEntity.ok(this.produtoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.produtoService.excluir(id);
    }

    // Upload de imagem: multipart/form-data com campo "img" (nome esperado pelo front)
    @PostMapping("/{id}/imagem")
    public ResponseEntity<ProdutoDetailDTO> fazerUploadImagem(
            @PathVariable Long id,
            @RequestParam("img") MultipartFile imagem) {
        return ResponseEntity.ok(this.produtoService.salvarImagem(id, imagem));
    }
}
