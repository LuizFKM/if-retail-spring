package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;

public record UserSummaryDTO(
        Long id,
        String name,
        String cpf,
        UserType tipo
) {
}
