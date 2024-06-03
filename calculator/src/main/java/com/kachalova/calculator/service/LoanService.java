package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface LoanService {
    List<LoanOfferDto> generateLoanOffers(@Valid LoanStatementRequestDto request, BigDecimal baseInterestRate);
}
