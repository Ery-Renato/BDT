package com.xtremeprojetos.bdt;

public class Motorista {

    private int id; // Agora o id é do tipo int, como no banco de dados
    private String matricula;
    private String nome;
    private String senha;

    // Construtor com id, matricula, nome e senha
    public Motorista(int id, String matricula, String nome, String senha) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.senha = senha;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
