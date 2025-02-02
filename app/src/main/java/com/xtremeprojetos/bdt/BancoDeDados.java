package com.xtremeprojetos.bdt;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDeDados extends SQLiteOpenHelper {

    // Nome do banco de dados e versão
    private static final String DATABASE_NAME = "bdt.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    private static final String TABLE_MOTORISTAS = "motoristas";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_MATRICULA = "matricula";
    private static final String COLUMN_SENHA = "senha";

    // Comando SQL para criar a tabela de motoristas
    private static final String CREATE_TABLE_MOTORISTAS =
            "CREATE TABLE " + TABLE_MOTORISTAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT NOT NULL, " +
                    COLUMN_MATRICULA + " TEXT NOT NULL, " +
                    COLUMN_SENHA + " TEXT NOT NULL);";

    // Construtor
    public BancoDeDados(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método obrigatório: cria o banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOTORISTAS);
    }

    // Método obrigatório: atualiza o banco de dados quando a versão muda
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOTORISTAS);
        onCreate(db);
    }
}
