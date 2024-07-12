package com.kachalova.deal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.deal.dto.EmailMessage;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.Theme;
import com.kachalova.deal.repos.StatementRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StatementRepo statementRepo;
    private final ObjectMapper objectMapper;

    private void sendMessage(UUID statementId, Theme theme) throws JsonProcessingException {
        log.info("KafkaProducer sendMessage request: statementId: {}, theme: {}", statementId, theme);
        Statement statement = statementRepo.findById(statementId);
        EmailMessage emailMessage = EmailMessage.builder()
                .address(statement.getClient().getEmail())
                .statementId(statementId)
                .theme(theme)
                .build();
        String jsonEmailDTO = objectMapper.writeValueAsString(emailMessage);
        String topic = getTopic(emailMessage.getTheme());
        kafkaTemplate.send(topic, jsonEmailDTO);
    }

    private String getTopic(Theme theme) {
        log.info("KafkaProducer getTopic request: theme: {}", theme);
        String topic = switch (theme) {
            case FINISH_REGISTRATION -> "finish-registration";
            case CREATE_DOCUMENTS -> "create-documents";
            case SEND_DOCUMENTS -> "send-documents";
            case SEND_SES -> "send-ses";
            case CREDIT_ISSUED -> "credit-issued";
            case STATEMENT_DENIED -> "statement-denied";
            default -> throw new IllegalStateException("Unexpected value: " + theme);
        };
        log.info("KafkaProducer getTopic response: theme: {}", theme);
        return topic;
    }

    public void finishRegistration(UUID statementId) throws JsonProcessingException {
        log.info("KafkaProducer finishRegistration: statementId: {}", statementId);
        sendMessage(statementId, Theme.FINISH_REGISTRATION);
    }

    public void createDocs(UUID statementId) throws JsonProcessingException {
        log.info("KafkaProducer createDocs: statementId: {}", statementId);
        sendMessage(statementId, Theme.CREATE_DOCUMENTS);
    }

    public void sendDocs(UUID statementId) throws JsonProcessingException {
        log.info("KafkaProducer sendDocs: statementId: {}", statementId);
        sendMessage(statementId, Theme.SEND_DOCUMENTS);
    }

    public void sendSes(UUID statementId) throws JsonProcessingException {
        log.info("KafkaProducer sendSes: statementId: {}", statementId);
        sendMessage(statementId, Theme.SEND_SES);
    }

    public void creditIssued(UUID statementId) throws JsonProcessingException {
        log.info("KafkaProducer creditIssued: statementId: {}", statementId);
        sendMessage(statementId, Theme.CREDIT_ISSUED);
    }
}
