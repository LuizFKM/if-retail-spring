package br.edu.ifpr.bsi.ifretailspring.domain.contato;

public record ContatoSummaryDTO(
        Long id,
        String telefone,
        String email,
        String whatsapp
) {
}
