package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class, PedidoMapper.class, ProdutoMapper.class})
public interface ClienteMapper {
    Cliente requestDTOToEntity(ClienteRequestDTO dto);
    ClienteDetailDTO entityToDetailDTO(Cliente cliente);
    ClienteSummaryDTO entityToSummaryDTO(Cliente cliente);
}
