package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;

public record ItemPedidoResponseDTO(
        Long ID,
        ProdutoSummaryDTO produto,
        int quantidade,
        double precoUnitario
) {}
