package com.kachalova.statement.controller;

import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;
import com.kachalova.statement.service.OfferSelection;
import com.kachalova.statement.service.Prescoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/statement")
@RequiredArgsConstructor
public class StatementController {

    private final Prescoring prescoring;
    private final OfferSelection offerSelection;

    @PostMapping()
    public List<LoanOfferDto> makePrescoring(@RequestBody LoanStatementRequestDto requestDto) {
        log.info("/statement request: {}", requestDto);
        List<LoanOfferDto> response = prescoring.sendLoanStatementRequestDto(requestDto);
        log.info("/statement response: {}", response);
        return response;
    }

    @PostMapping("/offer")
    public void selectOffer(@RequestBody LoanOfferDto loanOfferDto) {
        log.info("/offer/select request: {}", loanOfferDto);
        offerSelection.sendLoanOfferDto(loanOfferDto);
    }
}
