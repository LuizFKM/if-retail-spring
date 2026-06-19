package br.edu.ifpr.bsi.ifretailspring.services;

import br.edu.ifpr.bsi.ifretailspring.domain.auth.LoginRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.auth.LoginResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.factory.LoginResponseFactory;
import br.edu.ifpr.bsi.ifretailspring.domain.user.User;
import br.edu.ifpr.bsi.ifretailspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos"));

        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }

        // Factory Method: determina o tipo de resposta baseado no role
        return LoginResponseFactory.obter(user.getRole()).criar(user);
    }
}
