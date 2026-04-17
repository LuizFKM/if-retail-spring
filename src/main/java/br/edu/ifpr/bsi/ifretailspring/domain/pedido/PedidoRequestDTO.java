package br.edu.ifpr.bsi.ifretailspring.domain.pedido;

import java.util.List;

public record PedidoRequestDTO(
        Long clienteId,
        List<ItemPedidoRequestDTO> items
) {}
