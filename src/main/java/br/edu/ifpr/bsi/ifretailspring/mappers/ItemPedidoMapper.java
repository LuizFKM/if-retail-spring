package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedidoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedidoSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {
    ItemPedido requestDTOToEntity(ItemPedidoRequestDTO itemPedidoRequestDTO);

    ItemPedidoSummaryDTO entityToSummaryDTO(ItemPedido itemPedido);
}
