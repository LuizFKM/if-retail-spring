package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco requestDTOToEntity(EnderecoRequestDTO enderecoRequestDTO);

    EnderecoDetailDTO entityToDetailDTO(Endereco endereco);

    EnderecoSummaryDTO entityToSummaryDTO(Endereco endereco);
}
