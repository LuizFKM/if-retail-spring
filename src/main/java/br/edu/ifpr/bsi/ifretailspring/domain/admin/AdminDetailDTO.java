package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserDetailDTO;

import java.util.List;

public record AdminDetailDTO(
        Long id,
        String name,
        String cpf,
        String email,
        String matricula,
        String cargo,
        String setor,
        String role,
        // password nunca exposto no DetailDTO
        EnderecoSummaryDTO endereco,
        List<ContatoSummaryDTO> contatos
) implements UserDetailDTO {
}
