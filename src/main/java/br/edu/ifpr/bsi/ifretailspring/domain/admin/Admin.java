package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "tb_admin")
public class Admin extends User {
    private String matricula;
    private String setor;
    private String cargo;
    private String dataAdmissao;
    private boolean status;
}
