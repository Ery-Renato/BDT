package com.xtremeprojetos.bdt;

public class Motorista {

    private String id; // Novo campo id
    private String matricula;
    private String nome;
    private String senha;

    public Motorista(String matricula, String nome) {
        this.id = matricula;  // Usando a matrícula como id
        this.matricula = matricula;
        this.nome = nome;
    }

    public String getId() {
        return id; // Retorna o id (aqui, estamos considerando que o id é a matrícula)
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}
