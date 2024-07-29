package com.czavala.productmanagementsystem.services.email.impl;

import com.czavala.productmanagementsystem.dto.email.EmailDetails;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.services.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleEmail(EmailDetails emailDetails) throws MailException {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setFrom(emailDetails.getFrom());
//        mailMessage.setTo(emailDetails.getTo());
//        mailMessage.setSubject(emailDetails.getSubject());
//        mailMessage.setText(emailDetails.getMessageBody());
//        javaMailSender.send(mailMessage);
//        System.out.println("SimpleMail enviado exitosamente desde EmailServiceImpl: " + mailMessage);

        try {

            // Crea instancia de un MIME Message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            // Permite poblar la instancia MIME creada previamente
            // true indica que contiene archivo Multipart, enconding especifica la codificacion del contenido del mail
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            messageHelper.setFrom(emailDetails.getFrom());
            messageHelper.setTo(emailDetails.getTo());
            messageHelper.setSubject(emailDetails.getSubject());
            messageHelper.setText(emailDetails.getMessageBody(), true); // true indica que es HTML

            javaMailSender.send(mimeMessage);
            System.out.println("MIME Message enviado exitosamente desde EmailServiceImpl...");

        } catch (MessagingException e) {
            throw new MailException("Error al enviar email de bienvenida", e) {};
        }
    }

    @Override
    public EmailDetails getWelcomeEmailDetails(User user) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setFrom("no-reply@no-reply.com");
        emailDetails.setTo(user.getEmail());
        emailDetails.setSubject("Welcome to our e-commerce!");
//        emailDetails.setMessageBody("Dear " + user.getFirstname() + ",\n\n"
//                + "Welcome to our e-commerce!\n"
//                + "You can start shopping now.\n"
//                + "\nKind regards.");

        String messageBody = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }"
                + ".container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + ".header { text-align: center; }"
                + ".header img { max-width: 150px; margin-bottom: 12px; }"
                + ".content { padding: 15px; }"
                + ".content h3 { color: #333333; }"
                + ".content p { color: #555555; line-height: 1.5; }"
                + ".footer { text-align: center; padding: 20px; font-size: 12px; color: #999999; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "<div class=\"header\">"
                + "<img src=\"https://res.cloudinary.com/dwfxuhvq1/image/upload/v1721412269/email/welcome-email-img_ehzuig.jpg\" alt=\"Logo\">"
                + "</div>"
                + "<div class=\"content\">"
                + "<h3>Welcome to our e-commerce, " + user.getFirstname() + "!</h3>"
                + "<p>We are excited to have you with us.</p>"
                + "<p>Start exploring our wide range of products and enjoy an amazing shopping experience.</p>"
                + "<p>If you have any questions or need assistance, feel free to contact our support team.</p>"
                + "</div>"
                + "<div class=\"footer\">"
                + "<p>© 2024 Criszav E-commerce. All rights reserved.</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        emailDetails.setMessageBody(messageBody);
        emailDetails.setContentType("text/html");
        return emailDetails;
    }

    @Override
    public void sendWelcomeEmail(User user) {
    }
}
