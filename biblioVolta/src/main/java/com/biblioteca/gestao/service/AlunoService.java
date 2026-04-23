package com.biblioteca.gestao.service;

import com.biblioteca.gestao.model.Aluno;
import com.biblioteca.gestao.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorRgm(String rgm) {
        return alunoRepository.findByRgm(rgm);
    }

    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public boolean rgmJaCadastrado(String rgm) {
        return alunoRepository.existsByRgm(rgm);
    }
}
