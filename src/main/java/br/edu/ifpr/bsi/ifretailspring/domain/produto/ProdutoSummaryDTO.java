package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoSummaryDTO(
        Long ID,
        String descricao,
        double precoUnitario,
        boolean status
) {}
