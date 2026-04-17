package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;

import java.util.List;

public record ClienteRequestDTO(
        String name,
        String cpf,
        String password,
        EnderecoRequestDTO endereco,
        List<ContatoRequestDTO> contatoList
) {}
