package com.xtremeprojetos.bdt;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.graphics.pdf.PdfDocument;
import android.graphics.Paint;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RelatoriosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gera um relatório em PDF
        gerarRelatorioPDF();
    }

    private void gerarRelatorioPDF() {
        // Cria um novo documento PDF
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Desenha conteúdo no PDF
        Paint paint = new Paint();
        page.getCanvas().drawText("Relatório de Motoristas", 50, 50, paint);
        page.getCanvas().drawText("Este é um exemplo de relatório gerado.", 50, 100, paint);

        document.finishPage(page);

        // Salva o PDF no armazenamento
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "relatorio_motoristas.pdf");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            document.writeTo(fos);
            Toast.makeText(this, "Relatório gerado com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar relatório.", Toast.LENGTH_SHORT).show();
        }

        document.close();

        // Abre o PDF gerado usando uma intent
        abrirPDF(file);
    }

    private void abrirPDF(File file) {
        // Usa FileProvider para compartilhar o arquivo de forma segura
        Uri fileUri = FileProvider.getUriForFile(this, "com.xtremeprojetos.bdt.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível abrir o relatório.", Toast.LENGTH_SHORT).show();
        }
    }
}