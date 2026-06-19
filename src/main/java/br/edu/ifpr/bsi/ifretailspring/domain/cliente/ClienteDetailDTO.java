package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.produto.ProdutoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserDetailDTO;

import java.util.List;

public record ClienteDetailDTO(
        Long id,
        String name,
        String cpf,
        String email,
        String role,
        String urlFotoPerfil,
        EnderecoSummaryDTO endereco,
        List<ContatoSummaryDTO> contatos,
        List<PedidoSummaryDTO> pedidos,
        List<ProdutoSummaryDTO> favoritos
) implements UserDetailDTO {
}
