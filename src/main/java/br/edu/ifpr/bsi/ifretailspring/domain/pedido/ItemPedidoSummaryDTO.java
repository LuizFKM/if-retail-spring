package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;

import java.util.List;

public record ItemPedidoSummaryDTO(
        Long id,
        List<ProdutoSummaryDTO> protudos
) {
}
