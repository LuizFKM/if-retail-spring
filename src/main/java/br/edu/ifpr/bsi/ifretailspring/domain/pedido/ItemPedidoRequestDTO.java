package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

// produtoId resolvido manualmente no PedidoService (busca Produto pelo id)
public record ItemPedidoRequestDTO(
        Long produtoId,
        int quantidade
) {
}
