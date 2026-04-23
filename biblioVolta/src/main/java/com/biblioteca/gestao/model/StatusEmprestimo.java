package com.biblioteca.gestao.model;

public enum StatusEmprestimo {
    DISPONIVEL("Disponível"),
    EMPRESTADO("Emprestado"),
    DEVOLVIDO("Devolvido");

    private final String descricao;

    StatusEmprestimo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
