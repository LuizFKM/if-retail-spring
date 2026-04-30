package br.edu.ifpr.bsi.ifretailspring.domain.contato;

import br.edu.ifpr.bsi.ifretailspring.domain.GenericDomain;
import br.edu.ifpr.bsi.ifretailspring.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="tb_contatos")
public class Contato extends GenericDomain {
        private String telefone;
        private String email;
        private String whatsapp;


        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonIgnore
        private User user;
}
