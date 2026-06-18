package br.edu.ifpr.bsi.ifretailspring.domain.user;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="tb_user")
public abstract class User extends GenericDomain {
    @Column(name="name", nullable = false)
    private String name;

    @Column(name="CPF", unique = true)
    private String cpf;

    @Column(name="e-mail", unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="url_foto_perfil")
    private String urlFotoPerfil;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatoList = new ArrayList<>();

    public void adicionarContato(Contato contato) {
        contato.setUser(this);
        this.contatoList.add(contato);
    }

    public void removerContato(Contato contato) {
        contato.setUser(null);
        this.contatoList.remove(contato);
    }
}
