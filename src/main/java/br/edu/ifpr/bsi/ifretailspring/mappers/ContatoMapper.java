package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    Contato requestDTOToEntity(ContatoRequestDTO dto);
    ContatoResponseDTO entityToResponseDTO(Contato contato);
}
