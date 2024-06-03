package com.kachalova.calculator.controller;


import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
public class LoanOfferController {
    @Value("${app.keyRate}")
    BigDecimal keyRate;
    @Autowired
    private LoanService loanService;

    @PostMapping("/calculator/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto request) throws ScoringDataDtoValidationExc {
        log.info("Got LoanController{}", request.toString());
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(request, keyRate);
        log.info("Sent List<LoanOfferDto>{}", loanOfferDtoList.toString());
        return loanOfferDtoList;
    }
}
