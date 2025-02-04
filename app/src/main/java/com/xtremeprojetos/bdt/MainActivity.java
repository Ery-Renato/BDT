package com.xtremeprojetos.bdt;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Certifique-se de que este é o layout correto

        // Referências dos elementos da UI
        editTextUsuario = findViewById(R.id.editTextUsuario);

        // Exemplo de uso do campo de texto
        if (editTextUsuario != null) {
            editTextUsuario.setHint("Digite seu nome de usuário");
        }
    }
}