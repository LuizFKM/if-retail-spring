package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.produto.Produto;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoDetailDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto requestDTOToEntity(ProdutoRequestDTO produtoRequestDTO);
    ProdutoDetailDTO entityToDetailDTO(Produto produto);
}
