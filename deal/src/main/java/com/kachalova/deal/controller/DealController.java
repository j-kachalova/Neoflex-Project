package com.kachalova.deal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kachalova.deal.dto.FinishRegistrationRequestDto;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.service.FinishRegistration;
import com.kachalova.deal.service.KafkaProducer;
import com.kachalova.deal.service.LoanOfferCalculation;
import com.kachalova.deal.service.LoanOfferSelection;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deal")
public class DealController {
    private final LoanOfferCalculation loanOfferCalculation;
    private final LoanOfferSelection loanOfferSelection;
    private final FinishRegistration finishRegistration;
    public final KafkaProducer kafkaProducer;

    @Operation(summary = "расчёт возможных условий кредита")
    @PostMapping("/statement")
    public List<LoanOfferDto> calculateLoanOffer(@RequestBody LoanStatementRequestDto requestDto) {
        log.info("/statement request: {}", requestDto);
        List<LoanOfferDto> response = loanOfferCalculation.calculateLoanOffer(requestDto);
        log.info("/statement response: {}", response);
        return response;
    }

    @Operation(summary = "Выбор одного из предложений")
    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) throws JsonProcessingException {
        log.info("/offer/select request: {}", loanOfferDto);
        loanOfferSelection.selectOffer(loanOfferDto);
        kafkaProducer.finishRegistration(loanOfferDto.getStatementId());
    }

    @Operation(summary = "завершение регистрации + полный подсчёт кредита")
    @PostMapping("/calculate/{statementId}")
    public void completeRegistration(@RequestBody FinishRegistrationRequestDto requestDto, @PathVariable String statementId) throws JsonProcessingException {
        log.info("/calculate/{statementId} request: {}, statementId: {}", requestDto, statementId);
        finishRegistration.finishRegistration(requestDto, UUID.fromString(statementId));
        kafkaProducer.createDocs(UUID.fromString(statementId));
    }
}
