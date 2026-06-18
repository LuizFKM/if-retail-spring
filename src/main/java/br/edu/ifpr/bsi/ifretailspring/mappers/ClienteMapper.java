package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class})
public interface ClienteMapper {

    @Mapping(source = "contatos", target = "contatoList")
    Cliente requestDTOtoEntity(ClienteRequestDTO clienteRequestDTO);

    @Mapping(source = "contatoList", target = "contatos")
    @Mapping(source = "pedidoList", target = "pedidos")
    ClienteDetailDTO entityToDetailDTO(Cliente cliente);

    ClienteSummaryDTO entityToSummaryDTO(Cliente cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "contatos", target = "contatoList")
    void updateFromDto(ClienteRequestDTO dto, @MappingTarget Cliente entity);

    // Evita dependência circular com PedidoMapper (PedidoMapper usa ClienteMapper)
    default PedidoSummaryDTO pedidoToSummaryDTO(Pedido pedido) {
        if (pedido == null) return null;
        return new PedidoSummaryDTO(pedido.getId(), pedido.getDataDoPedido(), pedido.isStatus());
    }
}
