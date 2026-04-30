package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContatoMapper {
    Contato requestDTOToEntity(ContatoRequestDTO contatoRequestDTO);

    ContatoDetailDTO entityToDetailDTO(Contato contato);

    ContatoSummaryDTO entityToSummaryDTO(Contato contato);
}
