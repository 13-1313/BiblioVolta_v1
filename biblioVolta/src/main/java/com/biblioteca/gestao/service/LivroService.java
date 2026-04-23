package com.biblioteca.gestao.service;

import com.biblioteca.gestao.model.Aluno;
import com.biblioteca.gestao.model.Livro;
import com.biblioteca.gestao.model.StatusEmprestimo;
import com.biblioteca.gestao.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AlunoService alunoService;

    public LivroService(LivroRepository livroRepository, AlunoService alunoService) {
        this.livroRepository = livroRepository;
        this.alunoService = alunoService;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarPorStatus(StatusEmprestimo status) {
        return livroRepository.findByStatus(status);
    }

    public Optional<Livro> buscarPorId(String id) {
        return livroRepository.findById(id);
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void deletar(String id) {
        livroRepository.deleteById(id);
    }

    public Livro registrarEmprestimo(String id, String rgm, LocalDate dataDevolucaoPrevista) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Aluno aluno = alunoService.buscarPorRgm(rgm)
                .orElseThrow(() -> new RuntimeException("Aluno com RGM " + rgm + " não encontrado"));

        livro.setStatus(StatusEmprestimo.EMPRESTADO);
        livro.setNomeEmprestador(aluno.getNome());
        livro.setRgmEmprestador(aluno.getRgm());
        livro.setCursoEmprestador(aluno.getCurso());
        livro.setDataEmprestimo(LocalDate.now());
        livro.setDataDevolucaoPrevista(dataDevolucaoPrevista);

        return livroRepository.save(livro);
    }

    public Livro registrarDevolucao(String id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.setStatus(StatusEmprestimo.DEVOLVIDO);
        livro.setNomeEmprestador(null);
        livro.setRgmEmprestador(null);
        livro.setCursoEmprestador(null);
        livro.setDataEmprestimo(null);
        livro.setDataDevolucaoPrevista(null);

        return livroRepository.save(livro);
    }

    public Livro marcarDisponivel(String id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        livro.setStatus(StatusEmprestimo.DISPONIVEL);
        livro.setNomeEmprestador(null);
        livro.setRgmEmprestador(null);
        livro.setCursoEmprestador(null);
        livro.setDataEmprestimo(null);
        livro.setDataDevolucaoPrevista(null);

        return livroRepository.save(livro);
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
