package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record AdminRequestDTO(
        String nome,
        String cpf,
        String matricula,
        String cargo,
        String setor,
        LocalDate dataAdmissao,
        List<ContatoSummaryDTO> listaContato,
        EnderecoSummaryDTO endereco,
        String username,
        String role,
        String password
) implements UserRequestDTO {
}
