package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.ItemCarrinho;
import br.edu.ifpr.bsi.ifretailspring.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // READ - Buscar carrinho por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Carrinho> buscarPorId(@PathVariable Long id) {
        Carrinho carrinho = this.carrinhoService.buscarPorId(id);
        return ResponseEntity.ok(carrinho);
    }

    // CREATE - Criar um novo carrinho (POST)
    @PostMapping
    public ResponseEntity<Carrinho> criar(@RequestBody Carrinho request) {
        Carrinho carrinhoSalvo = this.carrinhoService.salvar(request);
        return ResponseEntity.ok(carrinhoSalvo);
    }

    // CREATE - Adicionar item ao carrinho (POST)
    @PostMapping("/{id}/itens")
    public ResponseEntity<Carrinho> adicionarItem(@PathVariable Long id,
                                                   @RequestBody ItemCarrinho item) {
        Carrinho carrinhoAtualizado = this.carrinhoService.adicionarItem(id, item);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

    // DELETE - Remover item do carrinho (DELETE)
    @DeleteMapping("/{id}/itens/{itemId}")
    public ResponseEntity<Carrinho> removerItem(@PathVariable Long id,
                                                 @PathVariable Long itemId) {
        Carrinho carrinhoAtualizado = this.carrinhoService.removerItem(id, itemId);
        return ResponseEntity.ok(carrinhoAtualizado);
    }

    // DELETE - Excluir um carrinho pelo ID (DELETE)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        this.carrinhoService.excluir(id);
    }
}
