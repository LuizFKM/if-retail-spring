package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "tb_clientes")
@PrimaryKeyJoinColumn(name = "user_id")
public class Cliente extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidoList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tb_clientes_favoritos",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> favoritos = new ArrayList<>();
}
