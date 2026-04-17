package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto requestDTOToEntity(ProdutoRequestDTO dto);
    ProdutoDetailDTO entityToDetailDTO(Produto produto);
    ProdutoSummaryDTO entityToSummaryDTO(Produto produto);
}
