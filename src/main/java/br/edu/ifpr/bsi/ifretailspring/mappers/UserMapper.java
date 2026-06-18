package br.edu.ifpr.bsi.ifretailspring.mappers;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.cliente.Cliente;
import br.edu.ifpr.bsi.ifretailspring.domain.user.User;
import br.edu.ifpr.bsi.ifretailspring.domain.user.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private AdminMapper adminMapper;

    public UserDetailDTO entityToDetailDTO(User user) {
        switch (user.getRole()) {
            case CLIENTE -> {
                return clienteMapper.entityToDetailDTO((Cliente) user);
            }
            case ADMIN -> {
                return adminMapper.entityToDetailDTO((Admin) user);
            }
        }
        throw new IllegalStateException("Tipo de usuário desconhecido: " + user.getClass());
    }

}
