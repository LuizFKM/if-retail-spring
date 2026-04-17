package br.edu.ifpr.bsi.ifretailspring.domain.admin;

public record AdminSummaryDTO(
        Long ID,
        String name,
        String cargo,
        String setor,
        boolean status
) {}
