package org.example.services;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;




    public class MailService {

        private static final Dotenv dotenv = Dotenv.load();  // charge automatiquement le fichier .env

        public static void sendVerificationEmail(String toEmail, String code) throws MessagingException {
            String fromEmail = dotenv.get("EMAIL_USERNAME");
            String password = dotenv.get("EMAIL_PASSWORD");

            if (fromEmail == null || password == null) {
                throw new RuntimeException("EMAIL_USERNAME ou EMAIL_PASSWORD n'est pas défini dans le fichier .env.");
            }

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Vérification de votre inscription");

            String content = "Bonjour,\n\nVoici votre code de vérification : " + code + "\n\nMerci.";
            message.setText(content);

            Transport.send(message);
        }
    }



