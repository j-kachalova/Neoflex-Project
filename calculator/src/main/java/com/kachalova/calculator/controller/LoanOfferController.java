package com.kachalova.calculator.controller;


import com.kachalova.calculator.config.PropertiesConfig;
import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoanOfferController {
    private final PropertiesConfig properties;
    private final LoanService loanService;

    @PostMapping("/calculator/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto request) throws ScoringDataDtoValidationExc {
        log.info("LoanStatementRequestDto request: {}", request);
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(request, properties.getKeyRate());
        log.info("List<LoanOfferDto> response: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }
}
