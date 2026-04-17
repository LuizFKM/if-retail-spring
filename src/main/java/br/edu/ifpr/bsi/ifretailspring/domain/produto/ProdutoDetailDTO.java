package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoDetailDTO(
        Long ID,
        String descricao,
        int quantidadeEmEstoque,
        double precoUnitario,
        boolean status
) {}
