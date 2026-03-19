package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name="tb_pedidos")
public class Pedido extends GenericDomain {
    private Date dataDoPedido;
    private Date dataDeEntregaDoPedido;
    private boolean status;
}
