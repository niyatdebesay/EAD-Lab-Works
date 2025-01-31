package com.example.Security.Salon.Utils;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service

public class EmailService {

    @Autowired
    private JavaMailSender mailSender;



    public void sendEmail(String to, String name, String SalonName) throws MessagingException, IOException {
        MimeMessage message =  mailSender.createMimeMessage();
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
        String htmlTemplate = readFile("src/main/resources/static/SalonOwnerNotification.html");
        htmlTemplate = htmlTemplate.replace("{{name}}", name);
        htmlTemplate = htmlTemplate.replace("{{SalonName}}", SalonName);
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    private String readFile(String filePath) throws  IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }


}
