package com.kachalova.deal.controller;

import com.kachalova.deal.dto.FinishRegistrationRequestDto;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.service.FinishRegistration;
import com.kachalova.deal.service.LoanOfferCalculation;
import com.kachalova.deal.service.LoanOfferSelection;
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

    @PostMapping("/statement")
    public List<LoanOfferDto> calculateLoanOffer(@RequestBody LoanStatementRequestDto requestDto) {
        log.info("/statement request: {}", requestDto);
        List<LoanOfferDto> response = loanOfferCalculation.calculateLoanOffer(requestDto);
        log.info("/statement response: {}", response);
        return response;
    }

    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("/offer/select request: {}", loanOfferDto);
        loanOfferSelection.selectOffer(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void completeRegistration(@RequestBody FinishRegistrationRequestDto requestDto, @PathVariable String statementId) {
        log.info("/calculate/{statementId} request: {}, statementId: {}", requestDto, statementId);
        finishRegistration.finishRegistration(requestDto, UUID.fromString(statementId));
    }
}
