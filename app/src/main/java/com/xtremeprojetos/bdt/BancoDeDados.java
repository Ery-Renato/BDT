package com.xtremeprojetos.bdt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

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

    // Método para inserir um motorista no banco de dados com senha em hash
    public boolean inserirMotorista(String nome, String matricula, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOME, nome);
        contentValues.put(COLUMN_MATRICULA, matricula);
        contentValues.put(COLUMN_SENHA, hashSenha(senha));  // Armazenando a senha como hash

        long resultado = db.insert(TABLE_MOTORISTAS, null, contentValues);
        db.close();

        return resultado != -1;  // Retorna true se a inserção for bem-sucedida
    }

    // Método para consultar um motorista verificando a matrícula e a senha em hash
    public boolean consultarMotorista(String matricula, String senhaHash) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] colunas = {COLUMN_MATRICULA, COLUMN_SENHA};
        String whereClause = COLUMN_MATRICULA + " = ?";
        String[] whereArgs = {matricula};

        Cursor cursor = db.query(TABLE_MOTORISTAS, colunas, whereClause, whereArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String senhaArmazenada = cursor.getString(cursor.getColumnIndex(COLUMN_SENHA));
            cursor.close();
            return senhaArmazenada.equals(senhaHash);  // Comparando o hash da senha armazenada
        }
        return false;
    }

    // Método para fazer o hash da senha
    private String hashSenha(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);  // Convertendo bytes para uma string hex
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para converter bytes em uma string hexadecimal
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
