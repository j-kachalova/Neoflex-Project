package com.kachalova.dossier.service.impl;

import com.kachalova.dossier.service.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    @Value("${spring.mail.username}")
    private String fromMail;
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendMail(String toMail, String subject, String message) throws MessagingException {
        log.info("MailSenderImpl: sendMail: toMail:{},subject: {}, message:{}", toMail, subject, message);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject(subject);

        Context context = new Context();
        context.setVariable("content", message);
        String processedString = templateEngine.process("email", context);
        log.debug("MailSenderImpl: sendMail: processedString:{}", processedString);
        mimeMessageHelper.setText(processedString, true);
        log.debug("MailSenderImpl: sendMail: mimeMessage:{}", mimeMessage);

        mailSender.send(mimeMessage);
        log.info("message send");
    }

    public void sendDocument(String toMail, String subject, String name) {
        log.info("MailSenderImpl: sendDocument: toMail:{},subject: {}, name:{}", toMail, subject, name);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setFrom(fromMail);

            message.setSubject(subject);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart fileBodyPart = new MimeBodyPart();

            DataSource fileDataSource = new FileDataSource("src/main/resources/docs/" + name);
            fileBodyPart.setDataHandler(new DataHandler(fileDataSource));
            fileBodyPart.setFileName(name + ".txt");
            multipart.addBodyPart(fileBodyPart);

            message.setContent(multipart);
            mailSender.send(message);
            log.info("message send");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
