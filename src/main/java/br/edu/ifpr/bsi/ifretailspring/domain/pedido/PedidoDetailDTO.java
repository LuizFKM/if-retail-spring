package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetailDTO(
        Long id,
        ClienteSummaryDTO cliente,
        List<ItemPedidoSummaryDTO> itens,
        LocalDateTime dataDoPedido,
        StatusPedido status
) {
}
