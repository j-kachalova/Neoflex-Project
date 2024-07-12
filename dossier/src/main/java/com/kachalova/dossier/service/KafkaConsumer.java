package com.kachalova.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.dossier.dto.EmailMessage;
import com.kachalova.dossier.service.impl.MailSenderImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final MailSenderImpl mailSender;
    private final ObjectMapper mapper;

    @KafkaListener(topics = "finish-registration", groupId = "myGroup")
    public void finishListener(String message) throws JsonProcessingException, MessagingException {
        log.info("KafkaConsumer finishListener: message:{}", message);
        EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
        log.debug("KafkaConsumer finishListener: emailMessage:{}", emailMessage);
        mailSender.sendMail(emailMessage.getAddress(), "finish registration", "завершите оформление");
    }

    @KafkaListener(topics = "create-documents", groupId = "myGroup")
    public void createDocsListener(String message) throws JsonProcessingException, MessagingException {
        log.info("KafkaConsumer createDocsListener: message:{}", message);
        EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
        log.debug("KafkaConsumer createDocsListener: emailMessage:{}", emailMessage);
        mailSender.sendMail(emailMessage.getAddress(),
                "create documents",
                "перейти к оформлению документов");
    }

    @KafkaListener(topics = "send-documents", groupId = "myGroup")
    public void sendDocsListener(String message) throws JsonProcessingException {
        log.info("KafkaConsumer sendDocsListener: message:{}", message);
        EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
        log.debug("KafkaConsumer sendDocsListener: emailMessage:{}", emailMessage);
        mailSender.sendDocument(emailMessage.getAddress(), "send documents", "Document");
    }

    @KafkaListener(topics = "send-ses", groupId = "myGroup")
    public void sendSesListener(String message) throws JsonProcessingException, MessagingException {
        log.info("KafkaConsumer sendSesListener: message:{}", message);
        EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
        String stringMessage = "Подписать документы, код ПЭП: " + UUID.randomUUID().toString();
        log.debug("KafkaConsumer sendSesListener: emailMessage:{}, stringMessage:{}", emailMessage, stringMessage);
        mailSender.sendMail(emailMessage.getAddress(), "send ses", stringMessage);
    }

    @KafkaListener(topics = "credit-issued", groupId = "myGroup")
    public void creditIssuedListener(String message) throws JsonProcessingException, MessagingException {
        log.info("KafkaConsumer creditIssuedListener: message:{}", message);
        EmailMessage emailMessage = mapper.readValue(message, EmailMessage.class);
        log.debug("KafkaConsumer creditIssuedListener: emailMessage:{}", emailMessage);
        mailSender.sendMail(emailMessage.getAddress(), "credit issued", "кредит успешно оформлен");
    }
}
