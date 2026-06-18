package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserRequestDTO;

import java.util.List;

public record ClienteRequestDTO(
        String name,
        String cpf,
        String email,
        String password,
        String urlFotoPerfil,
        String role,
        EnderecoRequestDTO endereco,
        List<ContatoRequestDTO> contatos
) implements UserRequestDTO {
}
