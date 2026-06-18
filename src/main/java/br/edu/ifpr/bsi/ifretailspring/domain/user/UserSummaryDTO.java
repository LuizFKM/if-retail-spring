package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;

public interface UserSummaryDTO{
    Long id();
    String username();
    String role();


}
