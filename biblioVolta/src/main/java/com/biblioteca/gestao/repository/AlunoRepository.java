package com.biblioteca.gestao.repository;

import com.biblioteca.gestao.model.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AlunoRepository extends MongoRepository<Aluno, String> {

    Optional<Aluno> findByRgm(String rgm);

    boolean existsByRgm(String rgm);
}
