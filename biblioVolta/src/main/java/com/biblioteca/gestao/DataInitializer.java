package com.biblioteca.gestao;

import com.biblioteca.gestao.model.Usuario;
import com.biblioteca.gestao.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioService usuarioService;

    public DataInitializer(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) {
        if (usuarioService.loginExiste("admin")) {
            // migra senha plain-text para BCrypt se necessário (upgrade transparente)
            usuarioService.migrarSenhaSePlanaTexto("admin", "admin123");
        } else {
            usuarioService.salvar(new Usuario("Administrador", "admin", "admin123"));
            System.out.println("======================================");
            System.out.println("  Usuário padrão criado:");
            System.out.println("  Login: admin");
            System.out.println("  Senha: admin123");
            System.out.println("======================================");
        }
    }
}
