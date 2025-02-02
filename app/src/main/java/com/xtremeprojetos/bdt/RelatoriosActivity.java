package com.xtremeprojetos.bdt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class RelatoriosActivity extends AppCompatActivity {

    private EditText etMesRelatorio;
    private Button btnGerarRelatorio;
    private Button btnEnviarRelatorio;
    private BancoDeDados bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorios);

        etMesRelatorio = findViewById(R.id.etMesRelatorio);
        btnGerarRelatorio = findViewById(R.id.btnGerarRelatorio);
        btnEnviarRelatorio = findViewById(R.id.btnEnviarRelatorio);

        bancoDeDados = new BancoDeDados(this);

        btnGerarRelatorio.setOnClickListener(v -> {
            String mes = etMesRelatorio.getText().toString().trim();
            if (!mes.isEmpty()) {
                gerarRelatorioPDF(mes);
            } else {
                Toast.makeText(this, "Digite o mês para gerar o relatório", Toast.LENGTH_SHORT).show();
            }
        });

        btnEnviarRelatorio.setOnClickListener(v -> {
            String mes = etMesRelatorio.getText().toString().trim();
            if (!mes.isEmpty()) {
                String destinatario = "email_do_chefe@exemplo.com"; // Substitua pelo e-mail correto
                String assunto = "Relatório Mensal de Tráfego - " + mes;
                String corpo = "Segue em anexo o relatório mensal de tráfego.";
                String caminhoArquivo = new File(getExternalFilesDir(null), "relatorio_" + mes + ".pdf").getAbsolutePath();
                enviarEmail(destinatario, assunto, corpo, caminhoArquivo);
            } else {
                Toast.makeText(this, "Digite o mês para enviar o relatório", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gerarRelatorioPDF(String mes) {
        try {
            Document document = new Document();
            String nomeArquivo = "relatorio_" + mes + ".pdf";
            File file = new File(getExternalFilesDir(null), nomeArquivo);
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            // Adiciona o título do relatório
            document.add(new Paragraph("Relatório Mensal de Tráfego - " + mes + "\n\n"));

            // Obtém os dados do banco de dados
            SQLiteDatabase db = bancoDeDados.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM motoristas WHERE strftime('%m', data) = ?", new String[]{mes});

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int colunaData = cursor.getColumnIndex("data");
                    int colunaDestino = cursor.getColumnIndex("percurso_destino");

                    if (colunaData != -1 && colunaDestino != -1) {
                        document.add(new Paragraph("Data: " + cursor.getString(colunaData)));
                        document.add(new Paragraph("Percurso/Destino: " + cursor.getString(colunaDestino)));
                        document.add(new Paragraph("-----------------------------------"));
                    }
                } while (cursor.moveToNext());

                cursor.close();
            } else {
                document.add(new Paragraph("Nenhum registro encontrado para este mês."));
            }

            db.close();
            document.close();

            Toast.makeText(this, "Relatório gerado: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar relatório", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarEmail(String destinatario, String assunto, String corpo, String caminhoArquivo) {
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                final String emailUsuario = "seu_email@gmail.com"; // Substitua pelo seu e-mail
                final String senhaUsuario = "sua_senha"; // **Use um token de app em vez de senha**

                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUsuario, senhaUsuario);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailUsuario));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                message.setSubject(assunto);

                // Corpo do e-mail
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(corpo);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // Adiciona o anexo
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(caminhoArquivo);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(new File(caminhoArquivo).getName());
                multipart.addBodyPart(messageBodyPart);

                message.setContent(multipart);

                // Envia o e-mail
                Transport.send(message);

                runOnUiThread(() -> Toast.makeText(RelatoriosActivity.this, "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show());
            } catch (MessagingException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(RelatoriosActivity.this, "Erro ao enviar e-mail", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
