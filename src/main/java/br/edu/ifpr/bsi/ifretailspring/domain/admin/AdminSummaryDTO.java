package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.user.UserSummaryDTO;

public record AdminSummaryDTO(
        Long id,
        String nome,
        String cargo,
        String matricula,
        String setor,
        String username,
        String role
) implements UserSummaryDTO {
}
