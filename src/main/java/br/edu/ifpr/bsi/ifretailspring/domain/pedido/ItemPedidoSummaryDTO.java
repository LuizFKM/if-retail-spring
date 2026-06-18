package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;

public record ItemPedidoSummaryDTO(
        Long id,
        ProdutoSummaryDTO produto,
        int quantidade  // campo adicionado — estava faltando
) {
}
