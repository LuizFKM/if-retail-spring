package br.edu.ifpr.bsi.ifretailspring.domain.contato;

public record ContatoResponseDTO(
        Long ID,
        String telefone,
        String email,
        String whatsapp
) {}
