package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemPedidoMapper.class})
public interface PedidoMapper {

    Pedido requestDTOToEntity(PedidoRequestDTO pedidoRequestDTO);

    PedidoDetailDTO entityToDetailDTO(Pedido pedido);

    PedidoSummaryDTO entityToSummaryDTO(Pedido pedido);

}
