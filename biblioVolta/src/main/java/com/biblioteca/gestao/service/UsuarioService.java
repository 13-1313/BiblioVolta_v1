package com.biblioteca.gestao.service;

import com.biblioteca.gestao.model.Usuario;
import com.biblioteca.gestao.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public Usuario salvar(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public boolean loginExiste(String login) {
        return usuarioRepository.findByLogin(login).isPresent();
    }

    public void migrarSenhaSePlanaTexto(String login, String senhaPlana) {
        usuarioRepository.findByLogin(login).ifPresent(u -> {
            if (!u.getSenha().startsWith("$2a$")) {
                u.setSenha(encoder.encode(senhaPlana));
                usuarioRepository.save(u);
            }
        });
    }
}
