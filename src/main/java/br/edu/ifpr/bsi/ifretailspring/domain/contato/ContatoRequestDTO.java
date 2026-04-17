package br.edu.ifpr.bsi.ifretailspring.domain.contato;

public record ContatoRequestDTO(
        String telefone,
        String email,
        String whatsapp
) {}
