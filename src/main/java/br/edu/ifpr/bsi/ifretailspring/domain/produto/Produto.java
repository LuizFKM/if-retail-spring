package br.edu.ifpr.bsi.ifretailspring.domain.produto;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="tb_produtos")
public class Produto extends GenericDomain {
    private String descricao;
    private int QuantidadeEmEstoque;
    private double precoUnitario;
    private boolean status;
}
