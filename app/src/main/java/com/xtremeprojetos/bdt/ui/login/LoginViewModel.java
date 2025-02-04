package com.xtremeprojetos.bdt.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xtremeprojetos.bdt.data.LoginRepository;
import com.xtremeprojetos.bdt.data.Result;
import com.xtremeprojetos.bdt.data.model.LoggedInUser;
import com.xtremeprojetos.bdt.Motorista;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<Result<LoggedInUser>> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;

    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<Result<LoggedInUser>> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // Realiza o login usando o repositório
        Motorista motorista = loginRepository.login(username, password);

        if (motorista != null) {
            // Converte o Motorista para LoggedInUser
            LoggedInUser loggedInUser = new LoggedInUser(motorista.getId(), motorista.getNome());
            loginResult.setValue(new Result.Success<>(loggedInUser));
        } else {
            // Define o resultado como erro
            loginResult.setValue(new Result.Error(new Exception("Login falhou")));
        }
    }

    public void logout() {
        loginRepository.logout();
    }
}