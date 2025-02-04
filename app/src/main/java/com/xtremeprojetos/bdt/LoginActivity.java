package com.xtremeprojetos.bdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etMatricula;
    private EditText etSenha;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa os campos de entrada
        etMatricula = findViewById(R.id.etMatricula);
        etSenha = findViewById(R.id.etSenha);

        // Inicializa o banco de dados passando o contexto da atividade
        bancoDeDados = new BancoDeDados(this);

        // Lógica para o botão de login
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém os valores dos campos
                String matricula = etMatricula.getText().toString().trim();
                String senha = etSenha.getText().toString().trim();

                // Valida os campos
                if (matricula.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Consulta o motorista no banco de dados
                Motorista motorista = bancoDeDados.consultarMotorista(matricula, senha);

                if (motorista != null) {
                    // Login bem-sucedido: Redireciona para a tela principal
                    Intent intent = new Intent(LoginActivity.this, TelaPrincipalActivity.class);
                    startActivity(intent);
                    finish(); // Fecha a tela de login
                } else {
                    // Login falhou: Exibe uma mensagem de erro
                    Toast.makeText(LoginActivity.this, "Matrícula ou senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lógica para o botão de cadastrar motorista
        findViewById(R.id.btnCadastrarMotorista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redireciona para a tela de cadastro de motorista
                Intent intent = new Intent(LoginActivity.this, CadastroMotoristaActivity.class);
                startActivity(intent);
            }
        });
    }
}