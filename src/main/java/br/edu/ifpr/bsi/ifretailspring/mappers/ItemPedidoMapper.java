package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.ItemPedidoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {
    ItemPedidoResponseDTO entityToResponseDTO(ItemPedido itemPedido);
}
