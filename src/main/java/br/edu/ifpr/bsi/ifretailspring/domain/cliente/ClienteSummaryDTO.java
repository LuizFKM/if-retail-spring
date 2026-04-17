package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoResponseDTO;

public record ClienteSummaryDTO(
        Long ID,
        String name,
        String cpf,
        EnderecoResponseDTO endereco
) {}
