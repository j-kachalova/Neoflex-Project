package com.kachalova.dossier.service;

import javax.mail.MessagingException;

public interface MailSender {
    void sendMail(String toMail, String subject, String message) throws MessagingException;

    void sendDocument(String toMail, String subject, String name);
}
