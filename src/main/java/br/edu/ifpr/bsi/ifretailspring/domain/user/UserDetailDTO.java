package br.edu.ifpr.bsi.ifretailspring.domain.user;

// username removido — User não tem campo username; email é o identificador real
public interface UserDetailDTO {
    Long id();
    String email();
    String role();
}
