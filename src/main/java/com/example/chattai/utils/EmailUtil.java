package com.example.chattai.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(username, "ChattAi Support");
        helper.setTo(email);

        String subject = "Here's the link to reset your password";
        String content = "Hello, You have requested to reset your password."
                + "Click the link below to change your password"
                + "<a href=\"" + resetPasswordLink + "\"> Change my password</a>" + "";

        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
