package com.biblioteca.gestao.repository;

import com.biblioteca.gestao.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);
}
