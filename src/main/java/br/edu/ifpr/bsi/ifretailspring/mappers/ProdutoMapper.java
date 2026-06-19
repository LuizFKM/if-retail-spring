package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto requestDTOToEntity(ProdutoRequestDTO produtoRequestDTO);
    ProdutoDetailDTO entityToDetailDTO(Produto produto);
    ProdutoSummaryDTO entityToSummaryDTO(Produto produto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ProdutoRequestDTO dto, @MappingTarget Produto entity);
}
