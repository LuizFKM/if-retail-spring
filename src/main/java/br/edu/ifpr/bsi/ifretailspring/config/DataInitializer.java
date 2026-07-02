//package br.edu.ifpr.bsi.ifretailspring.config;
//
//import br.edu.ifpr.bsi.ifretailspring.domain.admin.Admin;
//import br.edu.ifpr.bsi.ifretailspring.domain.enums.UserRole;
//import br.edu.ifpr.bsi.ifretailspring.repository.AdminRepository;
//import br.edu.ifpr.bsi.ifretailspring.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer { // <-- Removido o implements ApplicationRunner
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @EventListener(ApplicationReadyEvent.class) // <-- Agora sim o Spring vai esperar tudo ficar pronto
//    public void run() { // <-- Removido o ApplicationArguments args
//        try {
//            if (userRepository.findByEmail("admin@ifretail.com").isEmpty()) {
//                Admin admin = new Admin();
//                admin.setName("Administrador");
//                admin.setEmail("admin@ifretail.com");
//                admin.setPassword("admin123");
//                admin.setCpf("00000000000");
//                admin.setRole(UserRole.ADMIN);
//                adminRepository.save(admin);
//                System.out.println(">>> Admin de teste criado: admin@ifretail.com / admin123");
//            }
//        } catch (Exception e) {
//            System.err.println("Aviso: Não foi possível verificar/criar o admin básico. O banco já possui as tabelas? Erro: " + e.getMessage());
//        }
//    }
//}