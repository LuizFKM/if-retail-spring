package br.edu.ifpr.bsi.ifretailspring.domain.produto;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tb_produtos")
public class Produto extends GenericDomain {

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="quantidade", nullable = false)
    private int quantidadeEmEstoque;

    @Column(name="preco-unidade", nullable = false)
    private double precoUnitario;

    @Column(name="url-foto-produto")
    private String urlFotoProduto;

}
