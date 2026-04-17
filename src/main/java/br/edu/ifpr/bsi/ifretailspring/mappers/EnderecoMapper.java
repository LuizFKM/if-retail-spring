package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco requestDTOToEntity(EnderecoRequestDTO dto);
    EnderecoResponseDTO entityToResponseDTO(Endereco endereco);
}
