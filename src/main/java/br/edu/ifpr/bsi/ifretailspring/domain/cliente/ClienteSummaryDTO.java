package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;

public record ClienteSummaryDTO(
        Long id,
        String name,
        String cpf,
        EnderecoSummaryDTO endereco
) {
}
