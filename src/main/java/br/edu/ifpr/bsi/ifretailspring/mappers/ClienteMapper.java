package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class})
public interface ClienteMapper {
    Cliente requestDTOtoEntity(ClienteRequestDTO clienteRequestDTO);

    ClienteDetailDTO entityToDetailDTO(Cliente cliente);

    ClienteSummaryDTO entityToSummaryDTO(Cliente cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ClienteRequestDTO dto, @MappingTarget Cliente entity);

}
