package com.xtremeprojetos.bdt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroMotoristaActivity extends AppCompatActivity {

    // Declaração dos campos de entrada
    private EditText etNomeCadastro;
    private EditText etMatriculaCadastro;
    private EditText etSenhaCadastro; // Campo para a senha
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_motorista);

        // Inicializa os campos de entrada
        etNomeCadastro = findViewById(R.id.etNomeCadastro);
        etMatriculaCadastro = findViewById(R.id.etMatriculaCadastro);
        etSenhaCadastro = findViewById(R.id.etSenhaCadastro); // Inicializa o campo de senha

        // Inicializa o banco de dados passando o contexto da atividade
        bancoDeDados = new BancoDeDados(this);

        // Lógica para o botão de salvar
        findViewById(R.id.btnSalvarCadastro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtém os valores dos campos
                String nome = etNomeCadastro.getText().toString().trim();
                String matricula = etMatriculaCadastro.getText().toString().trim();
                String senha = etSenhaCadastro.getText().toString().trim(); // Obtém a senha

                // Valida os campos
                if (nome.isEmpty() || matricula.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(CadastroMotoristaActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insere o motorista no banco de dados
                boolean sucesso = bancoDeDados.inserirMotorista(nome, matricula, senha); // Salva a senha

                // Exibe uma mensagem de sucesso ou erro
                if (sucesso) {
                    Toast.makeText(CadastroMotoristaActivity.this, "Motorista " + nome + " cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish(); // Fecha a tela de cadastro após o sucesso
                } else {
                    Toast.makeText(CadastroMotoristaActivity.this, "Erro ao cadastrar motorista!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}