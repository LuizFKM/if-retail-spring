package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserDetailDTO;

import java.util.List;

public record ClienteDetailDTO(
    Long id,
    String name,
    String cpf,
    String username,
    String role,
    EnderecoSummaryDTO endereco,
    List<ContatoSummaryDTO> contatos,
    List<PedidoSummaryDTO> pedidos,
    String urlFotoPerfil

) implements UserDetailDTO {
}
