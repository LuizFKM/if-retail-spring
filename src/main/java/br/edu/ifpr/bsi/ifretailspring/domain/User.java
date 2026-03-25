package br.edu.ifpr.bsi.ifretailspring.domain;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tb_user")
public abstract class User extends GenericDomain {
    private String name;
    private String cpf;
    private String password; // Hash

    @Enumerated(EnumType.STRING)
    private UserType tipo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatoList = new ArrayList<>();
}
