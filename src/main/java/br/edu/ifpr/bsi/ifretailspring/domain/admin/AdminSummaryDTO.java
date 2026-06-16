package br.edu.ifpr.bsi.ifretailspring.domain.admin;

public record AdminSummaryDTO(
        Long id,
        String nome,
        String cargo,
        String matricula,
        String setor
) {
}
