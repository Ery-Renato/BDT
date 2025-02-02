package com.xtremeprojetos.bdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etMatriculaLogin, etSenhaLogin;
    private Button btnLogin;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referências dos elementos da UI
        etMatriculaLogin = findViewById(R.id.etMatriculaLogin);
        etSenhaLogin = findViewById(R.id.etSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // Inicializando o objeto BancoDeDados com o contexto da Activity
        bancoDeDados = new BancoDeDados(this);

        // Configurando o clique do botão de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLogin();
            }
        });
    }

    private void realizarLogin() {
        // Obtendo os valores dos campos de matrícula e senha
        String matricula = etMatriculaLogin.getText().toString().trim();
        String senha = etSenhaLogin.getText().toString().trim();

        // Verificando se os campos estão preenchidos
        if (matricula.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificando se a matrícula e a senha são válidas
        if (bancoDeDados.consultarMotorista(matricula, senha)) {

            // Login bem-sucedido
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Finaliza a LoginActivity para evitar que o usuário volte para ela
        } else {
            // Login falhou
            Toast.makeText(this, "Matrícula ou senha incorretos!", Toast.LENGTH_SHORT).show();
        }
    }
}
