package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetailDTO(
        Long ID,
        LocalDateTime dataDoPedido,
        LocalDateTime dataDeEntregaDoPedido,
        boolean status,
        ClienteSummaryDTO cliente,
        List<ItemPedidoResponseDTO> items
) {}
