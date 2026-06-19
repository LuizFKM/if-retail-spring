package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminSummaryDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class})
public interface AdminMapper {

    @Mapping(source = "contatos", target = "contatoList")
    Admin requestDTOToEntity(AdminRequestDTO adminRequestDTO);

    @Mapping(source = "contatoList", target = "contatos")
    AdminDetailDTO entityToDetailDTO(Admin admin);

    AdminSummaryDTO entityToSummaryDTO(Admin admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "contatos", target = "contatoList")
    void updateFromDto(AdminRequestDTO dto, @MappingTarget Admin entity);
}
