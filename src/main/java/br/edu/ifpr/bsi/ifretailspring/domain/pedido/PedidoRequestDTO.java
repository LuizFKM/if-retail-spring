package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.util.List;

// dataDoPedido vem do @CreationTimestamp; status é definido no service (true ao criar)
public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoRequestDTO> itens
) {
}
