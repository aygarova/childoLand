package com.kindergarten.kindergarten.common;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
    public static void main(String[] args) {
        // Set up SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use STARTTLS
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Port for TLS
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Trust the server

        // Enable debug mode to get detailed logs
        props.put("mail.debug", "true");


        // Set up authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("childoLand@gmail.com", "hrfw vxyt swzc nfar ");
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender address
            message.setFrom(new InternetAddress("childoLand@gmail.com"));

            // Set the recipient address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("gabriela.aygarova@gmail.com"));

            // Set the subject
            message.setSubject("Test Email from Java");

            // Set the email content
            message.setText("This is a test email sent from Java using Gmail SMTP.");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
