package br.edu.ifpr.bsi.ifretailspring.controllers;

import br.edu.ifpr.bsi.ifretailspring.domain.auth.LoginRequestDTO;
import br.edu.ifpr.bsi.ifretailspring.domain.auth.LoginResponseDTO;
import br.edu.ifpr.bsi.ifretailspring.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "https://if-retail-frontend.onrender.com")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
