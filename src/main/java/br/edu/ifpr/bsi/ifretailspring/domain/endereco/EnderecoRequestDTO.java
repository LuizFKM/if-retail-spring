package br.edu.ifpr.bsi.ifretailspring.domain.endereco;

public record EnderecoRequestDTO(
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep,
        String pais
) {}
