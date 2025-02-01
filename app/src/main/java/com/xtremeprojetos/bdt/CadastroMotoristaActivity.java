package com.xtremeprojetos.bdt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroMotoristaActivity extends AppCompatActivity {

    private EditText etNomeMotorista;
    private EditText etMatriculaMotorista;
    private Button btnSalvarMotorista;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_motorista);

        etNomeMotorista = findViewById(R.id.etNomeMotorista);
        etMatriculaMotorista = findViewById(R.id.etMatriculaMotorista);
        btnSalvarMotorista = findViewById(R.id.btnSalvarMotorista);

        bancoDeDados = new BancoDeDados(this);

        btnSalvarMotorista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNomeMotorista.getText().toString();
                String matricula = etMatriculaMotorista.getText().toString();

                if (nome.isEmpty() || matricula.isEmpty()) {
                    Toast.makeText(CadastroMotoristaActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = bancoDeDados.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("nome", nome);
                values.put("matricula", matricula);

                long id = db.insert("motoristas", null, values);
                db.close();

                if (id != -1) {
                    Toast.makeText(CadastroMotoristaActivity.this, "Motorista cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    etNomeMotorista.setText("");
                    etMatriculaMotorista.setText("");
                } else {
                    Toast.makeText(CadastroMotoristaActivity.this, "Erro ao cadastrar motorista", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}