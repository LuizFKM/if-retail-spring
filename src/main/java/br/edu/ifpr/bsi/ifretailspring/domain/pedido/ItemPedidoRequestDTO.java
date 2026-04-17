package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

public record ItemPedidoRequestDTO(
        Long produtoId,
        int quantidade,
        double precoUnitario
) {}
