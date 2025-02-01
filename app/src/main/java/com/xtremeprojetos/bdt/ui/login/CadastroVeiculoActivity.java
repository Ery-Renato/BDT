package com.xtremeprojetos.bdt;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroVeiculoActivity extends AppCompatActivity {

    private EditText etModeloVeiculo;
    private EditText etPlacaVeiculo;
    private Button btnSalvarVeiculo;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);

        etModeloVeiculo = findViewById(R.id.etModeloVeiculo);
        etPlacaVeiculo = findViewById(R.id.etPlacaVeiculo);
        btnSalvarVeiculo = findViewById(R.id.btnSalvarVeiculo);

        bancoDeDados = new BancoDeDados(this);

        btnSalvarVeiculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modelo = etModeloVeiculo.getText().toString();
                String placa = etPlacaVeiculo.getText().toString();

                if (modelo.isEmpty() || placa.isEmpty()) {
                    Toast.makeText(CadastroVeiculoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = bancoDeDados.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("modelo", modelo);
                values.put("placa", placa);

                long id = db.insert("veiculos", null, values);
                db.close();

                if (id != -1) {
                    Toast.makeText(CadastroVeiculoActivity.this, "Veículo cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    etModeloVeiculo.setText("");
                    etPlacaVeiculo.setText("");
                } else {
                    Toast.makeText(CadastroVeiculoActivity.this, "Erro ao cadastrar veículo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}