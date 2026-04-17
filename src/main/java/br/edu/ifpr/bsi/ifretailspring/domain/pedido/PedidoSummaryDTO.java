package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.time.LocalDateTime;

public record PedidoSummaryDTO(
        Long ID,
        LocalDateTime dataDoPedido,
        LocalDateTime dataDeEntregaDoPedido,
        boolean status,
        Long clienteId
) {}
