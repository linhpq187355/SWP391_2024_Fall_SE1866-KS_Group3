package com.homesharing.util;

import com.homesharing.exception.GeneralException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for sending emails using SMTP.
 * This class provides a method to send an email with a specified recipient,
 * subject, and content.
 */
public class SendingEmail {

    // Private constructor to prevent instantiation
    private SendingEmail() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Sends an email to the specified recipient with the given subject and content.
     *
     * @param to      The email address of the recipient.
     * @param subject The subject of the email.
     * @param content The content of the email, formatted as HTML.
     * @throws MessagingException If there is an error while sending the email.
     * @throws GeneralException   If there is an issue loading the configuration
     *                            file or if the file is not found.
     */
    public static void sendMail(String to, String subject, String content) throws MessagingException {
        Properties configProps = new Properties();
        try (InputStream input = SendingEmail.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new GeneralException("Unable to find config.properties");
            }
            configProps.load(input);
        } catch (IOException e) {
            throw new GeneralException("Error loading configuration file", e);
        }

        String senderMail = configProps.getProperty("email.sender");
        String senderPassword = configProps.getProperty("email.password");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a session with the specified properties and authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPassword);
            }
        });

        // Create a MimeMessage for the email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderMail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        // Send the email
        Transport.send(message);

    }
}
