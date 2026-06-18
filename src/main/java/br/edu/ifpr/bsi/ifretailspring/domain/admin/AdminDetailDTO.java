package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserDetailDTO;

import java.util.List;

public record AdminDetailDTO(
        Long id,
        String nome,
        String cpf,
        String matricula,
        String cargo,
        String setor,
        String role,
        String password,
        String username,
        List<ContatoSummaryDTO> listaContatos,
        EnderecoSummaryDTO endereco

) implements UserDetailDTO {
}
