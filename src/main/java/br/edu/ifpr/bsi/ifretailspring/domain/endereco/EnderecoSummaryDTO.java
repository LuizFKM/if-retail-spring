package br.edu.ifpr.bsi.ifretailspring.domain.endereco;

public record EnderecoSummaryDTO(
        Long id,
        String rua,
        String numero,
        String bairro,
        String cidade,
        String estado
) {
}
