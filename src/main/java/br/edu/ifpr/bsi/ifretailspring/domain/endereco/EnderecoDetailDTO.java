package br.edu.ifpr.bsi.ifretailspring.domain.endereco;

import br.edu.ifpr.bsi.ifretailspring.domain.cliente.ClienteSummaryDTO;

public record EnderecoDetailDTO(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String pais,
        ClienteSummaryDTO cliente
) {
}
