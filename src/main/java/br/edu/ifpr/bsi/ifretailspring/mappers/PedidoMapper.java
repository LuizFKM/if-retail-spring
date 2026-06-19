package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// requestDTOToEntity removido — construção de Pedido é feita manualmente no PedidoService
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ItemPedidoMapper.class})
public interface PedidoMapper {

    @Mapping(source = "items", target = "itens") // entidade usa "items", DTO usa "itens"
    PedidoDetailDTO entityToDetailDTO(Pedido pedido);

    PedidoSummaryDTO entityToSummaryDTO(Pedido pedido);
}
