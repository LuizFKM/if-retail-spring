package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.ItemCarrinho;
import br.edu.ifpr.bsi.ifretailspring.repository.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public Carrinho buscarPorId(Long id) {
        return this.carrinhoRepository.findByIdWithItens(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Carrinho não encontrado"));
    }

    public Carrinho salvar(Carrinho carrinho) {
        return this.carrinhoRepository.save(carrinho);
    }

    public Carrinho adicionarItem(Long carrinhoId, ItemCarrinho item) {
        Carrinho carrinho = this.carrinhoRepository.findByIdWithItens(carrinhoId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Carrinho não encontrado"));
        item.setCarrinho(carrinho);
        carrinho.getItens().add(item);
        return this.carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItem(Long carrinhoId, Long itemId) {
        Carrinho carrinho = this.carrinhoRepository.findByIdWithItens(carrinhoId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Carrinho não encontrado"));
        carrinho.getItens().removeIf(item -> item.getID().equals(itemId));
        return this.carrinhoRepository.save(carrinho);
    }

    @Transactional
    public void excluir(Long id) {
        this.carrinhoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Carrinho não encontrado"));
        this.carrinhoRepository.deleteById(id);
    }
}
