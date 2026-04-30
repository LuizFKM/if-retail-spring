package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;

import java.util.List;

public record UserDetailDTO(
        Long id,
        String nome,
        String cpf,
        UserType tipo,
        EnderecoSummaryDTO endereco,
        List<ContatoSummaryDTO> listaContatos
) {
}
