package com.biblioteca.gestao.repository;

import com.biblioteca.gestao.model.Livro;
import com.biblioteca.gestao.model.StatusEmprestimo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LivroRepository extends MongoRepository<Livro, String> {

    List<Livro> findByStatus(StatusEmprestimo status);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByAutorContainingIgnoreCase(String autor);
}
