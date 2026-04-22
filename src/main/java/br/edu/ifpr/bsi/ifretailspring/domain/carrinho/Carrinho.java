package br.edu.ifpr.bsi.ifretailspring.domain.carrinho;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tb_carrinho")
public class Carrinho extends GenericDomain {



    // mappedBy porque Cliente é o dono do relacionamento (possui @JoinColumn)
    @OneToOne(mappedBy = "carrinho")
    private Cliente cliente;

}
