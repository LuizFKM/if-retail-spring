package br.edu.ifpr.bsi.ifretailspring.domain.contato;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="tb_contatos")
public class contato extends GenericDomain {
    private String telefone;
    private String email;
    private String whatsapp;
}
