package br.edu.ifpr.bsi.ifretailspring.domain;

import br.edu.ifpr.bsi.ifretailspring.domain.endereco.Endereco;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.List;

@Data
@MappedSuperclass
public abstract class User extends GenericDomain {
    private String name;
    private String cpf;
    private List<Endereco> enderecoList;
    private List<Contato> contatoList;
    private int idade;
    private char senha;
    private boolean tipo;

}
