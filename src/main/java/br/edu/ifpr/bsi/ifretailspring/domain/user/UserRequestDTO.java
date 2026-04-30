package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;

import java.util.List;

public record UserRequestDTO(
        String name,
        String cpf,
        String password,
        UserType tipo,
        EnderecoSummaryDTO endereco,
        List<ContatoSummaryDTO> listaContatos
) {
}
