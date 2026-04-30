package br.edu.ifpr.bsi.ifretailspring.domain.endereco;

public record EnderecoSummaryDTO(
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado
) {
}
