package com.xtremeprojetos.bdt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroMotoristaActivity extends AppCompatActivity {

    private EditText etNomeCadastro;
    private EditText etMatriculaCadastro;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_motorista);

        // Inicializa os campos de entrada
        etNomeCadastro = findViewById(R.id.etNomeCadastro);
        etMatriculaCadastro = findViewById(R.id.etMatriculaCadastro);

        // Inicializa o banco de dados passando o contexto da atividade
        bancoDeDados = new BancoDeDados(this); // Passa o contexto da atividade atual

        // Lógica para o botão de salvar
        findViewById(R.id.btnSalvarCadastro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeCadastro.getText().toString();
                String matricula = etMatriculaCadastro.getText().toString();

                // Aqui você pode adicionar lógica para salvar os dados no banco
                // Exemplo de exibição de uma mensagem
                Toast.makeText(CadastroMotoristaActivity.this, "Motorista " + nome + " cadastrado!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
