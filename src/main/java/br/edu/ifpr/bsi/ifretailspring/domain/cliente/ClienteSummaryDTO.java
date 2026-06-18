package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserSummaryDTO;

public record ClienteSummaryDTO(
        Long id,
        String name,
        String cpf,
        EnderecoSummaryDTO endereco,
        String username,
        String role
) implements UserSummaryDTO {
}
