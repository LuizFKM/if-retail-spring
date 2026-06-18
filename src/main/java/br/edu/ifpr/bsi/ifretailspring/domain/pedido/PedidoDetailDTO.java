package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetailDTO(
        Long id,                        // era "ID" (maiúsculo) — corrigido
        ClienteSummaryDTO cliente,
        List<ItemPedidoSummaryDTO> itens,
        LocalDateTime dataDoPedido,    // era "dataPedido" — alinhado com a entidade
        boolean status
        // dataEntrega removida — campo não existe na entidade
) {
}
