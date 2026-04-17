package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.AdminSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class})
public interface AdminMapper {
    Admin requestDTOToEntity(AdminRequestDTO dto);
    AdminDetailDTO entityToDetailDTO(Admin admin);
    AdminSummaryDTO entityToSummaryDTO(Admin admin);
}
