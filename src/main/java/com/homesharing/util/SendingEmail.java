/*
 * Copyright(C) 2024, HomeSharing Project.
 * H.SYS:
 *  Home Sharing System
 *
 * Record of change:
 * DATE            Version             AUTHOR           DESCRIPTION
 * 2024-9-18      1.0                 ManhNC         First Implement
 */
package com.homesharing.util;

import com.homesharing.exception.GeneralException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Utility class for sending emails in the Home Sharing System.
 * This class provides a method to send emails with specified recipient, subject, and content.
 * All methods are static, and the class cannot be instantiated.
 *
 * @version 1.0
 * @since 2024-09-18
 * @author ManhNC
 */
public class SendingEmail {

    // Private constructor to prevent instantiation
    private SendingEmail() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Sends an email with the specified recipient, subject, and content.
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param content the content of the email (HTML format)
     * @throws MessagingException if an error occurs while sending the email
     * @throws GeneralException   if unable to find the configuration file or load properties
     */
    public static void sendMail(String to, String subject, String content) throws MessagingException {
        // Validate input parameters
        if (to == null || to.isEmpty()) {
            throw new GeneralException("Invalid email address");
        }
        if (subject == null || subject.isEmpty()) {
            throw new GeneralException("Email subject cannot be null or empty");
        }
        if (content == null) {
            throw new GeneralException("Email content cannot be null");
        }

        Properties configProps = new Properties();

        // Load email configuration properties from the config file
        try (InputStream input = SendingEmail.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new GeneralException("Unable to find config.properties");
            }
            configProps.load(input);
        } catch (IOException e) {
            throw new GeneralException("Error loading configuration file", e);
        }

        // Get sender's email and password from the properties file
        String senderMail = configProps.getProperty("email.sender");
        String senderPassword = configProps.getProperty("email.password");

        if (senderMail == null || senderMail.isEmpty()) {
            throw new GeneralException("Sender email cannot be null or empty in config");
        }
        if (senderPassword == null || senderPassword.isEmpty()) {
            throw new GeneralException("Sender password cannot be null or empty in config");
        }

        // Set up mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a session with an authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPassword);
            }
        });

        // Create a message and set its properties
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderMail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=UTF-8");

        // Send the message
        Transport.send(message);
    }
}
