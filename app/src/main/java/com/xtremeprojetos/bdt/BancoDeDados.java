package com.xtremeprojetos.bdt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "bdt.db";
    public static final int VERSAO_BANCO = 1;

    public BancoDeDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabelas
        db.execSQL("CREATE TABLE IF NOT EXISTS motoristas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "matricula TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS veiculos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "modelo TEXT," +
                "placa TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS bdts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data TEXT," +
                "percurso_destino TEXT," +
                "hora_saida TEXT," +
                "km_saida REAL," +
                "hora_chegada TEXT," +
                "km_chegada REAL," +
                "id_motorista INTEGER," +
                "id_veiculo INTEGER," +
                "FOREIGN KEY (id_motorista) REFERENCES motoristas(id)," +
                "FOREIGN KEY (id_veiculo) REFERENCES veiculos(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizar tabelas (se necessário)
    }
}