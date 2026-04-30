package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoRequestDTO(
    LocalDateTime dataPedido,
    Long clienteId,
    boolean Status,
    List<Long> itensId
) {
}
