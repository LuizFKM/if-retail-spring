package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "tb_clientes")
public class Cliente extends User {
    private Carrinho carrinho;
    private List<Pedido> pedidoList;
    private List<Produto> listaDeProdutosFavoritos;

}
