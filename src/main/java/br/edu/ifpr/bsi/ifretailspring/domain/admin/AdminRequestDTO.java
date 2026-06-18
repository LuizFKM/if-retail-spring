package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record AdminRequestDTO(
        String name,
        String cpf,
        String email,
        String password,
        String matricula,
        String cargo,
        String setor,
        LocalDate dataAdmissao,
        String role,
        EnderecoRequestDTO endereco,
        List<ContatoRequestDTO> contatos
) implements UserRequestDTO {
}
