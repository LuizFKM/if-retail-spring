package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoDetailDTO(
        Long id,
        String descricao,
        double precoUnitario,
        int quantidadeEmEstoque,
        boolean status
) {
}
