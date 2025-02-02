package com.xtremeprojetos.bdt;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Passa o contexto da MainActivity para o BancoDeDados
        BancoDeDados bancoDeDados = new BancoDeDados(this);  // Corrigido: passando o contexto (this)
    }
}
