package br.edu.ifpr.bsi.ifretailspring.domain.produto;

public record ProdutoRequestDTO(
        String descricao,
        int quantidadeEmEstoque,
        double precoUnitario,
        boolean status
) {}
