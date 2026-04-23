package com.biblioteca.gestao.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Document(collection = "livros")
public class Livro {

    @Id
    private String id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Autor é obrigatório")
    private String autor;

    private String isbn;

    private StatusEmprestimo status = StatusEmprestimo.DISPONIVEL;

    // Dados do empréstimo atual
    private String nomeEmprestador;
    private String rgmEmprestador;
    private String cursoEmprestador;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;

    public Livro() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public StatusEmprestimo getStatus() { return status; }
    public void setStatus(StatusEmprestimo status) { this.status = status; }

    public String getNomeEmprestador() { return nomeEmprestador; }
    public void setNomeEmprestador(String nomeEmprestador) { this.nomeEmprestador = nomeEmprestador; }

    public String getRgmEmprestador() { return rgmEmprestador; }
    public void setRgmEmprestador(String rgmEmprestador) { this.rgmEmprestador = rgmEmprestador; }

    public String getCursoEmprestador() { return cursoEmprestador; }
    public void setCursoEmprestador(String cursoEmprestador) { this.cursoEmprestador = cursoEmprestador; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
}
