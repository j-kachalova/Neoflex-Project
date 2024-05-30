package com.kachalova.calculator.controller;


import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;
    static Logger log = Logger.getLogger(LoanController.class.getName());
    @Value("${keyRate}")
    BigDecimal keyRate;
    @PostMapping("/calculator/offers")
    public List<LoanOfferDto> calculateLoanOffers(@Valid @RequestBody LoanStatementRequestDto request) {
        log.info("Got LoanController" + request.toString());
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(request,keyRate);
        log.info("Sended List<LoanOfferDto>" + loanOfferDtoList.toString());
        return loanOfferDtoList;
    }
}
