package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoSummaryDTO(
        Long id,
        String descricao,
        double precoUnitario
) {
}
