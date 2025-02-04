package com.xtremeprojetos.bdt.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.xtremeprojetos.bdt.BancoDeDados;
import com.xtremeprojetos.bdt.data.LoginDataSource;
import com.xtremeprojetos.bdt.data.LoginRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final BancoDeDados bancoDeDados;

    public LoginViewModelFactory(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            // Cria uma instância de LoginDataSource com o BancoDeDados
            LoginDataSource loginDataSource = new LoginDataSource(bancoDeDados);
            LoginRepository loginRepository = new LoginRepository(loginDataSource);
            return (T) new LoginViewModel(loginRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}