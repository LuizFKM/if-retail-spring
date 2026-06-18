package br.edu.ifpr.bsi.ifretailspring.domain.cliente;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserRequestDTO;

import java.util.List;

public record ClienteRequestDTO(
        String name,
        String cpf,
        String password,
        EnderecoRequestDTO enderco,
        List<ContatoRequestDTO> contatos,
        String role,
        String username,
        String urlFotoPerfil

) implements UserRequestDTO {
}
