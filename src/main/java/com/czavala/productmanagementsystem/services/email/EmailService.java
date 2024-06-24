package com.czavala.productmanagementsystem.services.email;

import com.czavala.productmanagementsystem.dto.email.EmailDetails;
import com.czavala.productmanagementsystem.persistance.entities.User;

public interface EmailService {

    void sendSimpleEmail(EmailDetails emailDetails);

    void sendWelcomeEmail(User user);
    EmailDetails getEmailDetails(User user);
}
