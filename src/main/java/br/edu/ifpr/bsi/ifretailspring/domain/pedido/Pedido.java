package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tb_pedidos")
public class Pedido extends GenericDomain {
    private LocalDateTime dataDoPedido;
    private LocalDateTime dataDeEntregaDoPedido;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;
    private boolean status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items = new ArrayList<>();

}
