package com.xtremeprojetos.bdt;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GeradorPDF {

    public static void criarPDF(Context context, String nomeArquivo, String conteudo) {
        try {
            // Diretório para salvar o PDF
            File diretorio = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "BDT");
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }

            // Caminho do arquivo
            File file = new File(diretorio, nomeArquivo + ".pdf");
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));

            // Criando documento PDF
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Adicionando conteúdo ao PDF
            document.add(new Paragraph(conteudo));

            // Fechando documento
            document.close();

            Log.d("GeradorPDF", "PDF gerado com sucesso: " + file.getAbsolutePath());

        } catch (FileNotFoundException e) {
            Log.e("GeradorPDF", "Erro ao criar o PDF", e);
        }
    }
}
