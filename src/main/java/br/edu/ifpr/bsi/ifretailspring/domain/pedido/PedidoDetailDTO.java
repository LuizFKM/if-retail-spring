package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetailDTO(
        Long ID,
        ClienteSummaryDTO cliente,
        List<ItemPedidoSummaryDTO> itens,
        LocalDateTime dataPedido,
        LocalDateTime dataEntrega,
        boolean status
) {
}
