package com.xtremeprojetos.bdt.data;

import android.content.Context;
import com.xtremeprojetos.bdt.BancoDeDados;
import com.xtremeprojetos.bdt.Motorista;

public class LoginDataSource {

    private BancoDeDados bancoDeDados;

    // Construtor que recebe um objeto BancoDeDados
    public LoginDataSource(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    // Método para realizar o login
    public Motorista login(String matricula, String senha) {
        String senhaHash = bancoDeDados.hashSenha(senha);
        return bancoDeDados.consultarMotorista(matricula, senhaHash);
    }

    // Método para realizar o logout
    public void logout() {
        // Limpa qualquer estado de sessão ou dados relacionados ao usuário logado
    }
}