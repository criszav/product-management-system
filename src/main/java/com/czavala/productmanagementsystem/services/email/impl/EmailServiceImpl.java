package com.czavala.productmanagementsystem.services.email.impl;

import com.czavala.productmanagementsystem.dto.email.EmailDetails;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(EmailDetails emailDetails) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailDetails.getFrom());
        mailMessage.setTo(emailDetails.getTo());
        mailMessage.setSubject(emailDetails.getSubject());
        mailMessage.setText(emailDetails.getMessageBody());
        javaMailSender.send(mailMessage);
        System.out.println("SimpleMail enviado exitosamente desde EmailServiceImpl: " + mailMessage);
    }

    @Override
    public EmailDetails getEmailDetails(User user) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setFrom("no-reply@no-reply.com");
        emailDetails.setTo(user.getEmail());
        emailDetails.setSubject("Welcome to our e-commerce!");
        emailDetails.setMessageBody("Dear " + user.getFirstname() + ",\n\n"
                + "Welcome to our e-commerce!\n"
                + "You can start shopping now.\n"
                + "\nKind regards.");
        return emailDetails;
    }

    @Override
    public void sendWelcomeEmail(User user) {
    }
}
