package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;

import java.util.List;

public record AdminDetailDTO(
        Long id,
        String nome,
        String cpf,
        String matricula,
        String cargo,
        String setor,
        UserType tipo,
        List<ContatoSummaryDTO> listaContatos,
        EnderecoSummaryDTO endereco

) {
}
