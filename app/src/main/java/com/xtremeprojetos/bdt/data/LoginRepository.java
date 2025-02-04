package com.xtremeprojetos.bdt.data;

import com.xtremeprojetos.bdt.Motorista;

public class LoginRepository {

    private LoginDataSource dataSource;

    public LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método para realizar o login
    public Motorista login(String matricula, String senha) {
        return dataSource.login(matricula, senha);
    }

    // Método para realizar o logout
    public void logout() {
        dataSource.logout();
    }
}