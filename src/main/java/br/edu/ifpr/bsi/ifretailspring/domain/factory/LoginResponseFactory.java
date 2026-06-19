package br.edu.ifpr.bsi.ifretailspring.domain.factory;

import br.edu.ifpr.bsi.ifretailspring.domain.auth.LoginResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.domain.user.User;

/**
 * Factory Method para autenticação.
 * Cada subclasse produz um LoginResponseDTO diferente conforme o role do usuário.
 */
public abstract class LoginResponseFactory {

    public abstract LoginResponseDTO criar(User user);

    // Factory Method: retorna a fábrica correta baseado no role
    public static LoginResponseFactory obter(UserRole role) {
        return switch (role) {
            case ADMIN -> new AdminLoginResponseFactory();
            case CLIENTE -> new ClienteLoginResponseFactory();
        };
    }

    // ── Fábrica para ADMIN ────────────────────────────────────────────────────
    private static class AdminLoginResponseFactory extends LoginResponseFactory {
        @Override
        public LoginResponseDTO criar(User user) {
            return new LoginResponseDTO(user.getId(), user.getName(), "ADMIN", "/painel");
        }
    }

    // ── Fábrica para CLIENTE ──────────────────────────────────────────────────
    private static class ClienteLoginResponseFactory extends LoginResponseFactory {
        @Override
        public LoginResponseDTO criar(User user) {
            return new LoginResponseDTO(user.getId(), user.getName(), "CLIENTE", "/perfil");
        }
    }
}
