package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.user.UserSummaryDTO;

public record AdminSummaryDTO(
        Long id,
        String name,
        String cargo,
        String matricula,
        String setor,
        String role
) implements UserSummaryDTO {
}
