package com.ecommerce.ecommercespringbootpostgre.service;

public interface MailService {
    void sendMail(String to, String subject, String text);
    void sendRegistrationConfirmMail(String to, String confirmLink, String firstname, String lastname);
}
