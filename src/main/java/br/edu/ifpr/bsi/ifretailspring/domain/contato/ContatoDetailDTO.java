package br.edu.ifpr.bsi.ifretailspring.domain.contato;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;

public record ContatoDetailDTO(
        Long id,
        String telefone,
        String email,
        String whatsapp,
        ClienteSummaryDTO cliente
) {
}
