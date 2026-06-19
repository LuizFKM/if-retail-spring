package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.time.LocalDateTime;

// Usado em ClienteDetailDTO.pedidos — campos alinhados com a entidade Pedido
public record PedidoSummaryDTO(
        Long id,
        LocalDateTime dataDoPedido, // era "data" — alinhado com entidade
        boolean status              // era clienteId — não faz sentido aqui
) {
}
