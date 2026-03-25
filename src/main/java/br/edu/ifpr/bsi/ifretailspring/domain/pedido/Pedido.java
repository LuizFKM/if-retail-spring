package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@Table(name="tb_pedidos")
public class Pedido extends GenericDomain {
    private Date dataDoPedido;
    private Date dataDeEntregaDoPedido;

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    private boolean status;
}
