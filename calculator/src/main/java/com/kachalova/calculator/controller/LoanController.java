package com.kachalova.calculator.controller;


import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;


    @PostMapping("/calculator/offers")
    public List<LoanOfferDto> calculateLoanOffers(@Valid @RequestBody LoanStatementRequestDto request) {

        return loanService.generateLoanOffers(request);
    }
}
