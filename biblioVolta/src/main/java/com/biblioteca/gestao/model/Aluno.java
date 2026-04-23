package com.biblioteca.gestao.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "alunos")
public class Aluno {

    @Id
    private String id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "RGM é obrigatório")
    @Indexed(unique = true)
    private String rgm;

    @NotBlank(message = "Curso é obrigatório")
    private String curso;

    public Aluno() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getRgm() { return rgm; }
    public void setRgm(String rgm) { this.rgm = rgm; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}
