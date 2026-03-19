package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name = "tb_clientes")
public class Cliente extends User {
    @OneToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidoList;
    private List<Produto> listaDeProdutosFavoritos;

}
