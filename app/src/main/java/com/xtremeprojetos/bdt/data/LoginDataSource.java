package com.xtremeprojetos.bdt.data;
import com.xtremeprojetos.bdt.data.model.LoggedInUser;
import com.xtremeprojetos.bdt.Motorista;
import com.xtremeprojetos.bdt.BancoDeDados;
import java.io.IOException;

public class LoginDataSource {

    private BancoDeDados bancoDeDados;

    public LoginDataSource(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public LoginDataSource() {

    }

    public Result<LoggedInUser> login(String matricula, String senha) {

        try {
            Motorista motorista = bancoDeDados.consultarMotorista(matricula, senha);
            if (motorista != null) {
                // Cria um usuário logado com o id do motorista
                LoggedInUser loggedInUser = new LoggedInUser(motorista.getId(), motorista.getNome());
                return new Result.Success<>(loggedInUser);
            } else {
                return new Result.Error(new IOException("Login ou senha inválidos"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Erro ao fazer login", e));
        }
    }

    public void logout() {
        // Revogar a autenticação
    }
}
