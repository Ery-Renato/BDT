package com.xtremeprojetos.bdt;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

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

        btnGerarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = etMesRelatorio.getText().toString();
                gerarRelatorioPDF(mes);
            }
        });

        btnEnviarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destinatario = "email_do_chefe@exemplo.com"; // Substitua pelo e-mail do seu chefe
                String assunto = "Relatório Mensal de Tráfego - " + etMesRelatorio.getText().toString();
                String corpo = "Segue em anexo o relatório mensal de tráfego.";
                String caminhoArquivo = new File(getExternalFilesDir(null), "relatorio_" + etMesRelatorio.getText().toString() + ".pdf").getAbsolutePath();
                enviarEmail(destinatario, assunto, corpo, caminhoArquivo);
            }
        });
    }

    private void gerarRelatorioPDF(String mes) {
        try {
            // Criando o arquivo PDF
            String nomeArquivo = "relatorio_" + mes + ".pdf";
            File file = new File(getExternalFilesDir(null), nomeArquivo);

            // Criando o documento PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Adicionando título ao relatório
            document.add(new Paragraph("Relatório Mensal de Tráfego - " + mes));

            // Obtendo dados do banco de dados
            SQLiteDatabase db = bancoDeDados.getReadableDatabase();
            Cursor cursor = db.query("bdts", null, "strftime('%m/%Y', data) = ?", new String[]{mes}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    // Adicionando dados ao relatório
                    document.add(new Paragraph("Data: " + cursor.getString(cursor.getColumnIndexOrThrow("data"))));
                    document.add(new Paragraph("Percurso/Destino: " + cursor.getString(cursor.getColumnIndexOrThrow("percurso_destino"))));
                    document.add(new Paragraph("-----------------------------------"));
                } while (cursor.moveToNext());
            }

            // Fechando cursor e banco de dados
            cursor.close();
            db.close();

            // Fechando o documento
            document.close();

            // Exibir mensagem de sucesso
            Toast.makeText(this, "Relatório gerado com sucesso: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao gerar relatório", Toast.LENGTH_SHORT).show();
        }
    }

    private void enviarEmail(String destinatario, String assunto, String corpo, String caminhoArquivo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Configurações do e-mail
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");

                    Session session = Session.getDefaultInstance(props,
                            new javax.mail.Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("seu_email@gmail.com", "sua_senha"); // Substitua pelo seu e-mail e senha
                                }
                            });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("seu_email@gmail.com")); // Seu e-mail
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                    message.setSubject(assunto);

                    // Criando corpo da mensagem
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setText(corpo);

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);

                    // Adicionando o anexo
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(caminhoArquivo);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(new File(caminhoArquivo).getName());
                    multipart.addBodyPart(messageBodyPart);

                    message.setContent(multipart);

                    // Enviando o e-mail
                    Transport.send(message);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RelatoriosActivity.this, "E-mail enviado com sucesso", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (MessagingException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RelatoriosActivity.this, "Erro ao enviar e-mail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
