package com.kindergarten.kindergarten.common;

import com.kindergarten.kindergarten.model.entity.Parent;
import com.kindergarten.kindergarten.services.ParentService;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    private String senderEmail = System.getenv("MAIL_SENDER_EMAIL");
    private String senderPassword = System.getenv("MAIL_PASS");

    public void sentEmail(List<String> parentsEmails, String subject, String description) {

        Session session = getSession();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));

            addRecipient(parentsEmails, message);

            message.setSubject(subject);
            message.setText(description);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.debug", "true");
        return properties;
    }

    private Session getSession() {
        Session session = Session.getInstance(setProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }

    private void addRecipient(List<String> parentsEmails, Message message) {
        parentsEmails.stream().forEach(
                recipientEmail -> {
                    try {
                        InternetAddress[] recipientAddresses = parentsEmails.stream()
                                .map(email -> {
                                    try {
                                        return new InternetAddress(email);
                                    } catch (AddressException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                })
                                .filter(Objects::nonNull)
                                .toArray(InternetAddress[]::new);

                        message.setRecipients(Message.RecipientType.TO, recipientAddresses);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
