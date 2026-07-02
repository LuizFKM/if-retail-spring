package br.edu.ifpr.bsi.ifretailspring.domain.admin;

import br.edu.ifpr.bsi.ifretailspring.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "tb_admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
    @Column(name = "matricula")
    private String matricula;
    @Column(name = "setor")
    private String setor;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "\"dataAdmissao\"")
    private LocalDate dataAdmissao;
}
