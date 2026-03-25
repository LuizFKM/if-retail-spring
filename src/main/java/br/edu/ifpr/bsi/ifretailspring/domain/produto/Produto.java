package br.edu.ifpr.bsi.ifretailspring.domain.produto;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tb_produtos")
public class Produto extends GenericDomain {
    private String descricao;
    private int quantidadeEmEstoque;
    private double precoUnitario;
    private boolean status;
}
