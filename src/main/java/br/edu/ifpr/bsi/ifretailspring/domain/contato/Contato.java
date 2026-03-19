package br.edu.ifpr.bsi.ifretailspring.domain.contato;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="tb_contatos")
public class Contato extends GenericDomain {
        private String telefone;
        private String email;
        private String whatsapp;

        @ManyToOne
        @JoinColumn(name="cliente_id")
        private Cliente cliente;
}
