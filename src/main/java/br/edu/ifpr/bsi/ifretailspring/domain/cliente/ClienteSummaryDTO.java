package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.user.UserSummaryDTO;

public record ClienteSummaryDTO(
        Long id,
        String name,
        String cpf,
        String role
) implements UserSummaryDTO {
}
