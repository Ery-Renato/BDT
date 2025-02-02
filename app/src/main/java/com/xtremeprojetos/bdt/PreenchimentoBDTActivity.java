package com.xtremeprojetos.bdt;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PreenchimentoBDTActivity extends AppCompatActivity {

    private EditText etPercursoDestino;
    private EditText etHorarioSaida;
    private EditText etKmSaida;
    private EditText etHorarioChegada;
    private EditText etKmChegada;
    private Spinner spMotorista;
    private Spinner spVeiculo;
    private Button btnSalvarBDT;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preenchimento_bdtactivity);

        etPercursoDestino = findViewById(R.id.etPercursoDestino);
        etHorarioSaida = findViewById(R.id.etHorarioSaida);
        etKmSaida = findViewById(R.id.etKmSaida);
        etHorarioChegada = findViewById(R.id.etHorarioChegada);
        etKmChegada = findViewById(R.id.etKmChegada);
        spMotorista = findViewById(R.id.spMotorista);
        spVeiculo = findViewById(R.id.spVeiculo);
        btnSalvarBDT = findViewById(R.id.btnSalvarBDT);

        bancoDeDados = new BancoDeDados(this);
        SQLiteDatabase db = bancoDeDados.getReadableDatabase();

        // Populando Spinner de Motoristas
        List<String> nomesMotoristas = new ArrayList<>();
        Cursor cursorMotoristas = db.query("motoristas", new String[]{"nome"}, null, null, null, null, null);
        if (cursorMotoristas.moveToFirst()) {
            do {
                nomesMotoristas.add(cursorMotoristas.getString(0));
            } while (cursorMotoristas.moveToNext());
        }
        cursorMotoristas.close();

        ArrayAdapter<String> adapterMotoristas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomesMotoristas);
        adapterMotoristas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMotorista.setAdapter(adapterMotoristas);

        // Populando Spinner de Veículos
        List<String> modelosVeiculos = new ArrayList<>();
        Cursor cursorVeiculos = db.query("veiculos", new String[]{"modelo"}, null, null, null, null, null);
        if (cursorVeiculos.moveToFirst()) {
            do {
                modelosVeiculos.add(cursorVeiculos.getString(0));
            } while (cursorVeiculos.moveToNext());
        }
        cursorVeiculos.close();

        ArrayAdapter<String> adapterVeiculos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modelosVeiculos);
        adapterVeiculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVeiculo.setAdapter(adapterVeiculos);

        db.close(); // Fechar o banco de dados após usar

        btnSalvarBDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String percursoDestino = etPercursoDestino.getText().toString();
                String horarioSaida = etHorarioSaida.getText().toString();
                String kmSaidaStr = etKmSaida.getText().toString();
                String horarioChegada = etHorarioChegada.getText().toString();
                String kmChegadaStr = etKmChegada.getText().toString();

                if (percursoDestino.isEmpty() || horarioSaida.isEmpty() || kmSaidaStr.isEmpty() ||
                        horarioChegada.isEmpty() || kmChegadaStr.isEmpty()) {
                    Toast.makeText(PreenchimentoBDTActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificação de Km
                double kmSaida = 0;
                double kmChegada = 0;
                try {
                    kmSaida = Double.parseDouble(kmSaidaStr);
                    kmChegada = Double.parseDouble(kmChegadaStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(PreenchimentoBDTActivity.this, "Valores de quilometragem inválidos", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idMotorista = spMotorista.getSelectedItemPosition() + 1; // Ajustar conforme a lógica do seu Spinner
                int idVeiculo = spVeiculo.getSelectedItemPosition() + 1; // Ajustar conforme a lógica do seu Spinner

                if (idMotorista == 0 || idVeiculo == 0) {
                    Toast.makeText(PreenchimentoBDTActivity.this, "Selecione motorista e veículo", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = bancoDeDados.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("data", obterDataAtual());
                values.put("percurso_destino", percursoDestino);
                values.put("hora_saida", horarioSaida);
                values.put("km_saida", kmSaida);
                values.put("hora_chegada", horarioChegada);
                values.put("km_chegada", kmChegada);
                values.put("id_motorista", idMotorista);
                values.put("id_veiculo", idVeiculo);

                long id = db.insert("bdts", null, values);
                db.close();

                if (id != -1) {
                    Toast.makeText(PreenchimentoBDTActivity.this, "BDT preenchido com sucesso", Toast.LENGTH_SHORT).show();
                    // Limpar os campos do formulário
                    etPercursoDestino.setText("");
                    etHorarioSaida.setText("");
                    etKmSaida.setText("");
                    etHorarioChegada.setText("");
                    etKmChegada.setText("");
                } else {
                    Toast.makeText(PreenchimentoBDTActivity.this, "Erro ao preencher BDT", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String obterDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}
