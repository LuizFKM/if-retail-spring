package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.Pedido;
import br.edu.ifpr.bsi.ifretailspring.domain.pedido.PedidoSummaryDTO;

import java.util.List;

public record ClienteDetailDTO(
    Long ID,
    String name,
    String cpf,
    UserType tipo,
    EnderecoSummaryDTO endereco,
    List<ContatoSummaryDTO> contatos,
    Carrinho carrinho,
    List<PedidoSummaryDTO> pedidos


) {
}
