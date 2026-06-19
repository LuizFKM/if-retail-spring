package br.edu.ifpr.bsi.ifretailspring.config;

import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
import br.edu.ifpr.bsi.ifretailspring.repository.AdminRepository;
import br.edu.ifpr.bsi.ifretailspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByEmail("admin@ifretail.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setName("Administrador");
            admin.setEmail("admin@ifretail.com");
            admin.setPassword("admin123");
            admin.setCpf("00000000000");
            admin.setRole(UserRole.ADMIN);
            adminRepository.save(admin);
            System.out.println(">>> Admin de teste criado: admin@ifretail.com / admin123");
        }
    }
}
