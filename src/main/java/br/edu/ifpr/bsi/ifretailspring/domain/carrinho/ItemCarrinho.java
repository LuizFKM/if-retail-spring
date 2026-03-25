package br.edu.ifpr.bsi.ifretailspring.domain.carrinho;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_itens_carrinho")
public class ItemCarrinho extends GenericDomain {

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    private int quantidade;
    private double precoUnitario;
}
