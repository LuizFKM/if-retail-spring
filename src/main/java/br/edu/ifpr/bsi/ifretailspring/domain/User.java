package br.edu.ifpr.bsi.ifretailspring.domain;

import br.edu.ifpr.bsi.ifretailspring.domain.contato.Contato;
import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public abstract class User extends GenericDomain {
    private String name;
    private String cpf;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecoList;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Contato> contatoList;
    private int idade;
    private char senha;
    private boolean tipo;

}
