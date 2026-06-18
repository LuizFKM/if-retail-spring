package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.ContatoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.EnderecoSummaryDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;

import java.util.List;

public interface UserDetailDTO{
        Long id();
        String username();
        String role();
}
