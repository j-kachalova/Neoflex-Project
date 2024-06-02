package com.kachalova.calculator.controller;


import com.kachalova.calculator.config.Properties;
import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import com.kachalova.calculator.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class LoanOfferController {
    @Value("${app.keyRate}")
    BigDecimal keyRate;
    @Autowired
    private Properties properties;
    @Autowired
    private LoanService loanService;
    static Logger log = Logger.getLogger(LoanOfferController.class.getName());

    @PostMapping("/calculator/offers")
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody LoanStatementRequestDto request) throws ScoringdataDtoValidationExc {
        log.info("Got LoanController" + request.toString());
        log.info("Got keyRate" + keyRate);
        log.info("Got properties" + properties.getKeyRate());
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(request,keyRate);
        log.info("Sended List<LoanOfferDto>" + loanOfferDtoList.toString());
        return loanOfferDtoList;
    }
}