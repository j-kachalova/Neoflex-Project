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
        log.info("LoanStatementRequestDto request: {}", requestDto);
        List<LoanOfferDto> response = loanOfferCalculation.calculateLoanOffer(requestDto);
        log.info("LoanOfferDto response: {}", response);
        return response;
    }

    @PostMapping("/offer/select")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("LoanOfferDto request: {}", loanOfferDto);
        loanOfferSelection.selectOffer(loanOfferDto);
    }

    @PostMapping("/calculate/{statementId}")
    public void completeRegistration(@RequestBody FinishRegistrationRequestDto requestDto, @PathVariable String statementId) {
        log.info("request with FinishRegistrationRequestDto request: {}, statementId: {}", requestDto, statementId);
        finishRegistration.finishRegistration(requestDto, statementId);
    }
}
