package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tb_admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
    private String matricula;
    private String setor;
    private String cargo;
    private LocalDate dataAdmissao;
    private boolean status;
}
