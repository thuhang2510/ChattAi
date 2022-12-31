package com.example.chattai.utils;

import com.example.chattai.respone.BasicRespone;
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

    public BasicRespone<String> sendEmail(String email, String resetPasswordLink){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String subject = "Here's the link to reset your password";
        String content = "Hello, You have requested to reset your password."
                + "Click the link below to change your password"
                + "<a href=\"" + resetPasswordLink + "\"> Change my password</a>" + "";

        try{
            helper.setFrom(username, "ChattAi Support");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
            return new BasicRespone<>("Send email success", 0, content);
        } catch (UnsupportedEncodingException ex) {
            return new BasicRespone<>(ex.getMessage(), -1, null);
        } catch (MessagingException e) {
            return new BasicRespone<>(e.getMessage(), -1, null);
        }
    }
}
