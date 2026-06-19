package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.enums.StatusPedido;

import java.time.LocalDateTime;

public record PedidoSummaryDTO(
        Long id,
        LocalDateTime dataDoPedido,
        StatusPedido status
) {
}
