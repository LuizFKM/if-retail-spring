package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoRequestDTO(
        String descricao,
        int QuantidadeEmEstoque,
        double precoUnitario,
        boolean status
) {
}
