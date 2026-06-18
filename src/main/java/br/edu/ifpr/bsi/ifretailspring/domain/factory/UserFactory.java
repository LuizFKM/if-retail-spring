package br.edu.ifpr.bsi.ifretailspring.domain.factory;

import br.edu.ifpr.bsi.ifretailspring.domain.user.User;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;

public class UserFactory {
    public static User createUser(UserRole type) {
        switch (type) {
            case ADMIN:
                Admin admin = new Admin();
                return admin;
            case CLIENTE:
                Cliente cliente = new Cliente();
                return cliente;
            default:
                throw new IllegalArgumentException("Tipo de usuário desconhecido");
        }
    }
}
