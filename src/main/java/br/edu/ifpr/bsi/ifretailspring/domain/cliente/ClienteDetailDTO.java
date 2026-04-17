package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;

import java.util.List;

public record ClienteDetailDTO(
        Long ID,
        String name,
        String cpf,
        EnderecoResponseDTO endereco,
        List<ContatoResponseDTO> contatoList,
        List<PedidoSummaryDTO> pedidoList,
        List<ProdutoSummaryDTO> favoritos
) {}
