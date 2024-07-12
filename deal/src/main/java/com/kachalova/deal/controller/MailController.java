package com.kachalova.deal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kachalova.deal.service.KafkaProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deal/document/{statementId}")
public class MailController {
    public final KafkaProducer kafkaProducer;

    @Operation(summary = "запрос на отправку документов")
    @PostMapping("/send")
    public void send(@PathVariable String statementId) throws JsonProcessingException {
        log.info("/send request: {}", statementId);
        kafkaProducer.sendDocs(UUID.fromString(statementId));
    }

    @Operation(summary = "запрос на подписание документов")
    @PostMapping("/sign")
    public void sign(@PathVariable String statementId) throws JsonProcessingException {
        log.info("/sign request: {}", statementId);
        kafkaProducer.sendSes(UUID.fromString(statementId));
    }

    @Operation(summary = "подписание документов")
    @PostMapping("/code")
    public void code(@PathVariable String statementId) throws JsonProcessingException {
        log.info("/code request: {}", statementId);
        kafkaProducer.creditIssued(UUID.fromString(statementId));
    }
}
