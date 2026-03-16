package br.edu.ifpr.bsi.ifretailspring.domain.endereco;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "tb_enderecos")
public class Endereco extends GenericDomain {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;
}
