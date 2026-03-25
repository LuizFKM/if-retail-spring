package br.edu.ifpr.bsi.ifretailspring.domain.factory;

import br.edu.ifpr.bsi.ifretailspring.domain.User;
import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.carrinho.Carrinho;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserType;

public class UserFactory {
    public static User createUser(UserType type) {
        switch (type) {
            case ADMIN:
                Admin admin = new Admin();
                admin.setStatus(true); // Default de um admin
                return admin;
            case CLIENTE:
                Cliente cliente = new Cliente();
                Carrinho carrinho = new Carrinho();
                carrinho.setCliente(cliente);
                cliente.setCarrinho(new Carrinho());
                return cliente;
            default:
                throw new IllegalArgumentException("Tipo de usuário desconhecido");
        }
    }
}
