package com.xtremeprojetos.bdt;

public class Motorista {
    private int id;
    private String matricula;
    private String nome;
    private String senha;

    public Motorista(int id, String matricula, String nome, String senha) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.senha = senha;
    }

    // Getters e setters (opcional)
    public int getId() {
        return id;
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