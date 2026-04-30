package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.time.LocalDateTime;

public record PedidoSummaryDTO(
        Long id,
        LocalDateTime data,
        Long clienteId

) {
}
