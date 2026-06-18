package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedidoSummaryDTO;
import org.mapstruct.Mapper;

// requestDTOToEntity removido — ItemPedido é construído manualmente no PedidoService
@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {
    ItemPedidoSummaryDTO entityToSummaryDTO(ItemPedido itemPedido);
}
